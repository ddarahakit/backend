# 따라하면서 배우는 IT - 온라인 강의 사이트

## 개발일지

#### 4월 13일 개발일지
+ ERD 설계 - 40% 완료
+ 개발 환경 구축 - 30% 완료
+ 강의 기능 - 40%

#### 4월 14일 개발일지
+ AWS S3에 이미지 업로드 구현
+ 강의 테이블에서 detail을 따로 테이블로 만듦

#### 4월 15일 개발일지
+ course detail 및 detail 이미지 추가 - detail에 대해서 고민...(테이블을 하나로 할 것이냐 두개로 할 것이냐)
+ chapter create 추가

#### 4월 16일 개발일지
+ 변수 이름 간단하게 수정
+ 이미지는 용도에 맞는 폴더에 업로드되도록 수정
+ course create할 때 course image와 course detail image 함께 올리게 수정

#### 4월 17일 개발일지
+ 회원가입 및 로그인 구현

#### 4월 18일 개발일지
+ 질문 기능 50% 완료

#### 4월 19일 개발일지
+ 사용자 닉네임 추가 및 JWT 클레임에 닉네임 추가

#### 4월 20일 개발일지
+ 질문 기능 로그인 한 사용자 정보 받아오도록 수정 및 질문 삭제 추가  






## 개발 고민
+ 테이블을 하나로 할 것이냐 두개로 할 것이냐  
    상세 내용은 항상 저장된다.  
    상세 내용은 항상 조회되는 것은 아니다.  
    특정 코스를 조회해야만 상세 내용이 조회된다.  

    테이블이 하나일 때 칼럼을 조회하지 않고 출력  
    테이블이 두개일 때 조인해서 출력
    DB에서의 성능  
    테이블이 두개면 결국 키를 표현하기 위한 중복 증가
    그리고 조인도 해야되니까 성능 저하...
  
+ 프론트에서 JWT를 local에 저장하면 생기는 보안문제(XSS)
    해결을 위해서 프론트에 대해서 생각도 좀 해봐야 할 듯
    쿠키에 저장하면 CSRF 문제가 생기는데 이건 시큐리티에서 CSRF 토큰으로 해결하면 될 듯
