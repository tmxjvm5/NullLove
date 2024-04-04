from flask import Flask, request, Response, redirect
from flask_cors import CORS
import io
import pickle
from configparser import Interpolation

import os
import sys
import urllib.request
import json
import pandas as pd
from pandas.io.json import json_normalize
import requests
import time
from datetime import datetime
from selenium import webdriver as wb
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from urllib.request import urlretrieve
from tqdm import tqdm_notebook
from bs4 import BeautifulSoup as bs
from datetime import datetime as dt

from collections import Counter # 갯수 세어주는 라이브러리 사용
import re
from konlpy.tag import Okt, Komoran

# !pip install konlpy
from PyKomoran import *
komoran = Komoran("EXP")

# tf-idf
from sklearn.feature_extraction.text import TfidfVectorizer
# cosine_similarity
from sklearn.metrics.pairwise import cosine_similarity

# anaconda prompt실행 > conda install -c pytorch pytorch 입력 
# pip install -U sentence-transformers
import torch
from sentence_transformers import SentenceTransformer, util
from gensim.corpora import Dictionary
import numpy as np
from transformers import AutoTokenizer, AutoModel

from itertools import islice

## [File Load] ##############################################################

# 상위-하위기관 목록
org_json = open('government_organization_chart.json', encoding = 'utf-8')
organization = json.load(org_json)	#=> 파이썬 자료형(딕셔너리나 리스트)으로 반환

# 불용어 리스트 불러오기
f = open('stop_words2.txt', 'r', encoding='utf-8')
stop_words = f.read().splitlines()

## [크롤링 함수] ##############################################################

# 기업명, 키워드, 상위기관명 입력 함수
def inputs():
    company = input("기업명을 입력하세요: ") # 기업명 입력
    keyword = input("키워드를 입력하세요: ") # 기업 키워드 입력
    org = input("상위기관을 입력하세요: ") # 상위기관명 입력
    return company, keyword, org

# 하위 기관 리스트 반환 함수
def get_under_orgs(org):
    under_org = organization[org]
    return under_org

# 명사를 추출하는 함수 (기업, 기관 명사 추출시 사용) 
def get_names_nouns(name):
    name_nouns = komoran.get_nouns(name) # 명사추출
    return name_nouns

# 명사를 추출하는 함수 (기업, 기관 명사 추출시 사용) 
def get_names_nouns(name):
    name_nouns = komoran.get_nouns(name) # 명사추출
    return name_nouns

# 검색어를 합쳐주는 함수
def get_search(a, b):
    search = a + " " + b
    return search

# 네이버 뉴스 url 받아오는 함수
def get_naver_url(search, l):
    client_id = "wqsYzpIXir3gW5wCABV9"
    client_secret = "4QA3crferT"

    encText = urllib.parse.quote(search) # 위에서 합친 단어

    url = 'https://openapi.naver.com/v1/search/news?query='+encText+'&display=100' # 관련도순

    request = urllib.request.Request(url)
    request.add_header('X-Naver-Client-Id', client_id)
    request.add_header('X-Naver-Client-Secret', client_secret)
    response = urllib.request.urlopen(request)
    rescode = response.getcode()
    if(rescode==200):
        response_body = response.read()
        json_str = response_body.decode('utf-8')
    else:
        print("Error Code:" + rescode)

    json_object = json.loads(json_str) #json 변환
    if json_object['total'] == 0: # 검색 기사가 없을 경우를 처리할 로직
        print("검색 기사 없음")
        return []
    
    df = pd.DataFrame(json_object['items'])
    
    # 기간 필터링
    df['pubDate'] = df['pubDate'].astype('datetime64[ns]')
    now = datetime.now()
    df['pubDate'] = now - df['pubDate']
    df['pubDate'] = df['pubDate'].dt.days
    df = df[df['pubDate']<90]
    
    naver_links={}
    for i in range(len(df)):
        if 'n.news.naver.com' in df['link'][i]: # 네이버 뉴스 url만 가져오기
            naver_links[df['link'][i]]=df['title'][i]
        if l < 40:
            if len(naver_links) == 5: # url을 5개까지만 크롤링
                return naver_links
        else:
            if len(naver_links) == 3: # url을 3개까지만 크롤링
                return naver_links
    return naver_links

# 본문 크롤링 함수
def get_contents(naver_links):
    driver = wb.Chrome()
    news_contents={}
    for url in naver_links.keys(): # 네이버 뉴스 본문 크롤링
        try:
            driver.get(url)
            time.sleep(1)
            content = driver.find_element(By.CLASS_NAME,'go_trans._article_content')
            content = content.text
            content = content.replace('\n', '')
            news_contents[url]=content
        except:
            continue
    return news_contents

# 명사 당 빈도수가 들어간 리스트를 구해주는 함수
def get_count_nouns(news_contents):
    cnt_nouns = {}
    nouns=[] # 명사를 담을 리스트
    for i in news_contents.keys():
        nouns = komoran.get_nouns(news_contents[i]) # 기사의 명사들을 리스트에 담음
        cnt = Counter(nouns) # 명사 당 개수 (딕셔너리 타입)
        cnt_nouns[i] = list(cnt.items())
    return cnt_nouns 

# 명사 당 빈도수가 들어간 리스트를 구해주는 함수2
def get_count_nouns2(news_contents):
    cnt_nouns = [] # 각 기사의 명사 당 개수를 담을 리스트
    nouns=[] # 명사를 담을 리스트
    for i in news_contents: # 각 기사 반복문 수행
        nouns = komoran.get_nouns(i) # 기사의 명사들을 리스트에 담음
        cnt = Counter(nouns) # 명사 당 개수 (딕셔너리 타입)
    return cnt 

# 기업명 or 기관명, 키워드의 빈도수를 구해주는 함수
def get_total_count(cnt_nouns, name_nouns, key_nouns):
    cnt1 = 0 # 기관 이름의 갯수를 넣을 변수
    cnt2 = 0 # 키워드의 갯수를 넣을 변수
    total_cnt = 0
    cnt = {} # 각 기사 별로 위에서 구한 빈도를 넣을 리스트
    
    for i in cnt_nouns.keys():
        for j in cnt_nouns[i]:
            if j[0] in name_nouns: # 단어가 기업명의 추출된 명사일 경우
                cnt1 = j[1] # 해당 단어의 갯수를 변수에 저장
            if j[0] in key_nouns: ## 단어가 키워드의 추출된 명사일 경우
                cnt2 = j[1]  # 해당 단어의 갯수를 변수에 저장
            total_cnt = cnt1 + cnt2
        cnt[i] = [cnt1, cnt2, total_cnt]
    return cnt

# 적합도를 만족하는지 확인하는 함수 (단어 최소 빈도) (기관용 기사 적합도 판정)
def check_count_news(cnt):
    flags = []
    for i in cnt.keys():
        if cnt[i][0]>0 and cnt[i][1]>0: # 기관 이름, 키워드 모두 최소 하나씩을 들어가 있어야 함
            flags.append(i)  
    return flags # 적합도를 만족하는 기사들의 url을 반환

# 가장 큰 빈도수(적합도)를 갖는 기사를 선택하는 함수 (기업용 대표기사) 
def max_count_news(cnt):
    m = -1 # 최대값
    flag = -1 # 몇 번쨰 기사인지 구하기 위한 변수

    for i in cnt.keys(): # 기사의 갯수만큼 반복문 수행
        if cnt[i][0]>0 and cnt[i][1]>0: # 기관 이름, 키워드 모두 최소 하나씩을 들어가 있어야 함
            if m<cnt[i][2]: # 최대값을 구하기
                m = cnt[i][2]
                flag = i
    return flag

# 기업 대표 기사를 구해주는 함수
def get_company_result(company, keyword):
    com_nouns = get_names_nouns(company) # 기업명 명사추출
    key_nouns = get_names_nouns(keyword) # 키워드 명사추출
    
    search = get_search(company, keyword) # 검색어
    naver_links = get_naver_url(search, 0) # 네이버 뉴스 링크
    
    if len(naver_links) == 0:
        
        return { }
    else:
        news_contents = get_contents(naver_links) # 본문 크롤링
        cnt_nouns = get_count_nouns(news_contents) # 본문의 단어 당 빈도수
        cnt = get_total_count(cnt_nouns, com_nouns, key_nouns) # 이름, 키워드 단어 빈도수
        flag = max_count_news(cnt) # 적합도를 만족하는 기사 인덱스
        
        if flag != -1: # 빈도수가 제일 높은 기사가 있을 경우
            
            return news_contents[flag]
        else: # 적합기사가 없을 경우
            return []

# 각 기관들의 대표 기사를 구해주는 함수
def get_orgs_result(under_orgs, keyword):
    # 아래 기관 개수 세는 변수들은 최종 크롤링에서 뺄 예정 (오류날까봐 임시로 둔 것)
    cnt_no_url = 0 # url(크롤링할 기사)이 없는 기관의 개수
    cnt_no_content = 0 # url(크롤링할 기사)은 있지만 적합도를 만족하지 못해 선택된 본문이 없는 기관의 개수
    cnt_yes_content = 0 # url(크롤링할 기사)도 있고 적합도를 만족하는 본문이 있어 최종적으로 하나의 기사를 픽한 기관의 개수
    
    org_news_dict= {} # 기관명: [픽된 기사] 를 넣을 딕셔너리 생성
    url ={} # 기관명: 네이버 기사 제목&링크를 넣을 딕셔너리
    
    key_nouns = get_names_nouns(keyword) # 키워드 명사추출
    l = len(under_orgs)
    for o in under_orgs:
        orgs_results = [] # 적합도를 만족하는 기사를 넣을 딕셔너리
        
        under_nouns = get_names_nouns(o) # 기관명 명사추출
        
        search = get_search(o, keyword) # 검색어
        naver_links = get_naver_url(search, l) # 네이버 뉴스 링크
        
        if len(naver_links) == 0: # 링크가 없으면 
            cnt_no_url +=1 # url(크롤링할 기사)이 없는 기관의 개수 1씩 증가
        else: # 링크가 있으면
            url[o] = naver_links
            news_contents = get_contents(naver_links) # 본문 크롤링
            cnt_nouns = get_count_nouns(news_contents) # 본문의 단어 당 빈도수
            cnt = get_total_count(cnt_nouns, under_nouns, key_nouns) # 기관명, 키워드 단어 빈도수
            flags = check_count_news(cnt) # 적합도를 만족하는 기사 url
            if len(flags) != 0: # 적합도를 만족하는 기사가 하나라도 있을 경우
                cnt_yes_content += 1 # url(크롤링할 기사)도 있고 적합도를 만족하는 본문이 있어 최종적으로 하나의 기사를 픽한 기관의 개수 1씩 증가
                for i in flags:
                    orgs_results.append([i,news_contents[i]])
                org_news_dict[o] = orgs_results # 적합도를 만족하는 기사들 리스트를 딕셔너리에 넣음
            else: # 적합도 만족x
                cnt_no_content += 1 # # url(크롤링할 기사)은 있지만 적합도를 만족하지 못해 선택된 본문이 없는 기관의 개수 1씩 증가
            
    return org_news_dict, url

## [TF-IDF 함수] ##############################################################

# 전처리
# 특수문자 제거 함수
def delete_special(s):
    return re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣0-9a-zA-Z ]','',s)

# 불용어 제거 함수 
def delete_stop_words(word_tokens, stop_words):
    result = []
    for w in word_tokens:
        if w not in stop_words:
            result.append(w)
    return result

# TF-IDF 전처리
def preprocessing(s):
    tmp = delete_special(s) # 특수문자 제거
    tmp = komoran.get_nouns(s) # 명사 추출
    return tmp

# SBERT 전처리
def preprocessing2(s, stop_words):
    tmp = delete_special(s) # 특수문자 제거
    tmp = komoran.get_nouns(tmp) # 명사 추출
    tmp = delete_stop_words(tmp, stop_words) # 불용어 제거
    return " ".join(tmp)

# 기업 대표기사와 한 하위기관의 적합도를 만족하는 모든 기사들을 하나의 리스트에 담는 함수
def get_documents(company_news, orgs_news):
    s = ' '.join(preprocessing(company_news)) # 전처리 후 문자열로 변환
    documents = [s] # 기업과 기관의 뉴스들을 한 번에 모아둘 리스트
    for i in orgs_news: # 기관 뉴스 반복문 수행
        s = ' '.join(preprocessing(i)) # 전처리 후 문자열로 변환
        documents.append(s)
    return documents

# TF-IDF and cosine_similarity
def get_similarity(documents, stop_words):
    tfidf = TfidfVectorizer(stop_words= stop_words) # 모델 생성
    tfidf_matrix = tfidf.fit_transform(documents) # tf-idf 임베딩 벡터 생성?
    cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix) # 코사인 유사도
    return cosine_sim[0]

# 최대 유사도를 가진 기사의 인덱스를 반환하는 함수
def get_max_index(cosine_sim):
    m = -1
    flag = -1
    for i in range(1, len(cosine_sim)):
        if m < cosine_sim[i]:
            m = cosine_sim[i]
            flag = i
    return flag

# 기관의 대표기사를 반환하는 함수 => 전처리된 본문을 가져오기 때문에 주석처리해놨음
def get_orgs_result2(documents, flag):
    print(flag)
    return [documents[flag]]

## [SBERT 함수] ############################################################## 

# mean pooling 함수
def mean_pooling(model_output, attention_mask):
    token_embeddings = model_output[0] 
    input_mask_expanded = attention_mask.unsqueeze(-1).expand(token_embeddings.size()).float()
    return torch.sum(token_embeddings * input_mask_expanded, 1) / torch.clamp(input_mask_expanded.sum(1), min=1e-9)

# 대표기사 토큰화 함수
def tokenize_news(represent_news, tokenizer):
    token = tokenizer(represent_news, padding=True, truncation=True, return_tensors='pt')
    
    return token

# 임베딩값 구하는 함수
def embeddings(embeddings):
    for Y in range(len(embeddings)):
        return embeddings[Y]
    

# 기업용 기사 임베딩값 구하기
def get_company_embeddings(company_result, tokenizer, model):
    d = preprocessing2(company_result, stop_words)
    token = tokenize_news(d, tokenizer)
    with torch.no_grad(): # 연산기록중지 → 연산속도가 빨라짐
        model_output = model(**token)

    com_embeddings = mean_pooling(model_output, token['attention_mask'])
    x_com = embeddings(com_embeddings)
    
    return x_com

# 기관용 기사 임베딩값 구하기 
def get_org_embeddings(orgs_result_result_dict, tokenizer, model):
    test_dict = {} # 기관 대표기사들 임베딩 값 넣어둘 리스트
    for o in orgs_result_result_dict.keys():
        d = preprocessing2(orgs_result_result_dict[o][0][1], stop_words)
        token = tokenize_news(d, tokenizer)

        with torch.no_grad(): 
            model_output = model(**token)

        org_embeddings = mean_pooling(model_output, token['attention_mask'])
        y_org = embeddings(org_embeddings)
        test_dict[o] = y_org
    return test_dict

# 기업-기관의 대표기사간의 코사인 유사도 구하기 
def get_cosine_simil(x_com, test_dict):
    result_dict = {}
    for i in test_dict.keys():
        cosine = torch.nn.CosineSimilarity(dim=0)
        cosine_output = cosine(x_com, test_dict[i])
        print((i),"Cosine Similarity : ",cosine_output,"\n")
        result_dict[i] = cosine_output
    return result_dict

def sorting_orgs(result_dict):
    sorting = sorted(result_dict.items(), key=lambda x:x[1], reverse = True)
    sorted_result = []
    for i in sorting:
        sorted_result.append(i[0])
        if len(sorted_result) == 5:
            break
    return sorted_result

## [추천페이지용 함수] #########################################################

# 최종결과 유사도 % 리스트 구하는 함수
def cosineResult_orgs(sorted_result,result_dict):
    orgCosine = []
    # reverseCosine = [] #progressbar용 100-orgConsine 값 넣을 리스트
    for i in range(len(sorted_result)):
        orgCosine.append(int(round(result_dict[sorted_result[i]].item(),2)*100))
        # reverseCosine.append(100-int(round(result_dict[sorted_result[i]].item(),2)*100))
    return orgCosine 

# progressbar용 100-orgConsine 값 구하는 함수
def reverseCosine_org(orgCosine):
    reverseCosine = []
    for i in range(len(orgCosine)):
        reverseCosine.append(int(100-orgCosine[i]))
    return reverseCosine

# 최종 제공 기관기사 {기관명:[url, 제목, 본문]} 딕셔너리 반환 함수
def get_last_result(orgs_result_result_dict,url, sorted_result):
    last_result = {}
    for i in sorted_result:
        urls = orgs_result_result_dict[i][0][0]
        title = url[i]
        title = title[urls]
        content = orgs_result_result_dict[i][0][1]
        last_result[i] = [urls, title, content]
    
    return last_result

# 최종 제공 기관기사 딕셔너리 내용 접근 함수
def accessNews_orgs(sorted_result,last_result):
    orgUrl = []
    orgTitle = []
    orgContent = []
    for i in range(len(sorted_result)):
        orgUrl.append(last_result[sorted_result[i]][0]) # url 접근
        orgTitle.append(last_result[sorted_result[i]][1]) # 제목 접근
        orgContent.append(last_result[sorted_result[i]][2]) # 원문 접근
    return orgUrl, orgTitle, orgContent

# 최고 유사도 기관 대표기사 단어, 빈도 딕셔너리 구하는 함수
def frequency(sorted_result,last_result):
    fre_list = []
    for i in range(len(sorted_result)):
        fre_list.append(preprocessing2(last_result[sorted_result[i]][2],stop_words)) # 전처리한 원문 리스트
        fre_dic = get_count_nouns2(fre_list) # 명사추출 딕셔너리
        sort_fre_dic = sorted(fre_dic.items(), key=lambda x: x[1], reverse=True) # 내림차순 정렬
        frequency_dict = dict(islice(dict(sort_fre_dic).items(),20)) # 20개 까지로 분할
    return frequency_dict

# 최고 유사도 기관 대표기사 단어, 빈도 접근
def accessFre_orgs(frequency_dict):
    fre_value = []
    fre_key = []
    for i in range(len(frequency_dict)):
        fre_key.append(list(frequency_dict)[i])
        fre_value.append(frequency_dict[list(frequency_dict)[i]])
    return fre_key, fre_value

## [Flask 서버 구동] ##########################################################

app = Flask(__name__)
        
CORS(app)
@app.route('/org', methods = ['GET', 'POST'])
def org():
    if request.method == 'GET':
        
        company = request.args['company']
        keyword = request.args['keyword']
        org = request.args['org']
        
        under_orgs = get_under_orgs(org)
        company_result = get_company_result(company, keyword) # 기업용 대표 기사
        orgs_result_dict, url = get_orgs_result(under_orgs, keyword) # 기관용 대표 기사 기관명:[url, 기사본문] / 타이틀&url
    
        abc=[]
        # 기관의 대표기사를 선정하기 위한 함수 구동
        orgs_result_result_dict={} # 각 기관의 대표 기사를 담을 딕셔너리
        for i in orgs_result_dict.keys(): # 기관을 한 번씩 방문
            temp=[]
            for j in orgs_result_dict[i]:
                temp.append(j[1])
        
            documents = get_documents(company_result, temp)
            cosine_sim = get_similarity(documents, stop_words)
            flag = get_max_index(cosine_sim)
            # news = get_orgs_result2(documents, flag) => 전처리된 본문
            # orgs_result_result_dict[i] = news
            
            orgs_result_result_dict[i] = [orgs_result_dict[i][flag-1]] # 전처리되지 않은 원문 / 함수 수행 결과 얻은 flag는 기업 기사가 포함되어 인덱스가 하나씩 늘어난 것이므로 다시 1을 빼주어야 함
                
            abc.append(flag)
            
        # 학습된 모델 경로
        pretrained_model_path = "sbert/training_klue_sts_klue-roberta-base-2022-08-17_23-27-13"

        # 모델과 토크나이저 로드
        model = AutoModel.from_pretrained(pretrained_model_path)
        tokenizer = AutoTokenizer.from_pretrained(pretrained_model_path)

        x_com = get_company_embeddings(company_result, tokenizer, model) # 기업 대표기사 임베딩 벡터
        test_dict = get_org_embeddings(orgs_result_result_dict, tokenizer, model) # 기관 대표기사 임베딩 벡터
        result_dict = get_cosine_simil(x_com, test_dict) 
        
        sorted_result = sorting_orgs(result_dict)  
        last_result = get_last_result(orgs_result_result_dict,url, sorted_result)
        
        orgCosine = cosineResult_orgs(sorted_result,result_dict) 
        reverseCosine = reverseCosine_org(orgCosine)
        orgUrl, orgTitle, orgContent = accessNews_orgs(sorted_result,last_result)
        frequency_dict = frequency(sorted_result,last_result)
        fre_key, fre_value = accessFre_orgs(frequency_dict)
        
        # 전송할 데이터 json 값으로 변환
        data = {"sorted_result" : sorted_result, "orgCosine" : orgCosine, "reverseCosine" : reverseCosine,
                "orgUrl" : orgUrl, "orgTitle" : orgTitle, "orgContent" : orgContent, "frequency_dict" : frequency_dict,
                "fre_key" : fre_key, "fre_value" : fre_value}
        json_data = json.dumps(data)
        
        return json_data

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8089)