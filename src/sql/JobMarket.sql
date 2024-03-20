    -- 조회하기
    select * from user_tables;
    select * from tab;

    -- 취업우대
    CREATE TABLE TBL_PRIORITY (
    priority_code NUMBER(1) NOT NULL -- 취업우대코드
    , priority_name NVARCHAR2(10) -- 취업우대명
    , constraint PK_tbl_priority_priority_code primary key(priority_code) -- priority_code P.K 지정
    );
    -- Table TBL_PRIORITY이(가) 생성되었습니다.


    -- 학력 
    CREATE TABLE TBL_ACADEMY (
    academy_code NUMBER(1) NOT NULL -- 학력코드
    , academy_name NVARCHAR2(10) NOT NULL -- 학력명
    , constraint PK_tbl_academy_academy_code primary key(academy_code) -- academy_code P.K 지정
    );
    -- Table TBL_ACADEMY이(가) 생성되었습니다.
    
    
    -- 구직자 
    CREATE TABLE TBL_USER_INFO (
    user_id VARCHAR2(10) NOT NULL -- 개인아이디
    , user_passwd VARCHAR2(20) NOT NULL -- 비밀번호
    , user_name NVARCHAR2(10) NOT NULL -- 성명
    , user_address NVARCHAR2(100) -- 주소
    , user_tel VARCHAR2(15) NOT NULL -- 연락처
    , user_security_num VARCHAR2(20) NOT NULL -- 주민번호
    , user_email VARCHAR2(30) NOT NULL -- 이메일
    , fk_priority_code NUMBER(1) -- 취업우대코드
    , fk_academy_code NUMBER(1) -- 학력코드
    , constraint PK_user_info_user_id primary key(user_id) -- user_id P.K 지정
    , constraint FK_user_info_fk_priority_code foreign key(fk_priority_code) references tbl_priority(priority_code) -- fk_priority_code F.K 지정
    , constraint FK_user_info_fk_academy_code foreign key(fk_academy_code) references tbl_academy(academy_code) -- fk_academy_code F.K 지정
    );
    -- Table TBL_USER_INFO이(가) 생성되었습니다.
    
    
    -- 구직자로그인
    CREATE TABLE TBL_USER_LOGIN (
    fk_user_id VARCHAR2(10) NOT NULL -- 개인아이디
    , user_passwd VARCHAR2(20) NOT NULL -- 비밀번호
    , user_name NVARCHAR2(10) NOT NULL -- 성명
    , constraint PK_user_login_fk_user_id primary key(fk_user_id) -- fk_user_id P.K 지정
    , constraint FK_user_login_fk_user_id foreign key(fk_user_id) references tbl_user_info(user_id) -- fk_user_id F.K 지정
    );
    -- Table TBL_USER_LOGIN이(가) 생성되었습니다.


    -- 지역 
    create table tbl_local 
   (local_code NUMBER(3) NOT NULL -- 지역코드
   , local_name NVARCHAR2(10) NOT NULL -- 지역명
   , city_name NVARCHAR2(10) NOT NULL  -- 도시명 
   , constraint PK_tbl_local_localcode primary key(local_code)   -- local_code P.K 지정
    );
    -- Table TBL_LOCAL이(가) 생성되었습니다.


     --   자격증상세등록 테이블 생성 
    CREATE TABLE TBL_LICENSE_DETAIL (
   license_code NVARCHAR2(20) NOT NULL, -- 자격증코드 
   license_name NVARCHAR2(15) NOT NULL, -- 자격증명 
   license_day DATE NOT NULL, -- 취득일자 
   license_company NVARCHAR2(20) NOT NULL, -- 발급기관 
   CONSTRAINT PK_LICENSE_DETAIL_LICENSE_CODE PRIMARY KEY (license_code),   --   자격증코드 PK 지정
   CONSTRAINT UK_LICENSE_DETAIL_LICENSE_NAME UNIQUE (license_name)   --   자격증코드 UK 지정
    );
   -- Table TBL_LICENSE_DETAIL이(가) 생성되었습니다.


    --   이력서 테이블 생성 
    CREATE TABLE TBL_PAPER (
   paper_code NUMBER(6) NOT NULL, -- 이력서코드 
   fk_user_id VARCHAR2(10) NOT NULL, -- 개인아이디 
   fk_license_code NVARCHAR2(20), -- 자격증코드 
   fk_local_code NUMBER(3) NOT NULL, -- 지역코드 
   user_security_num VARCHAR2(20), -- 주민번호(역정) 
   career NVARCHAR2(5) NOT NULL, -- 신입/경력 여부 
   hope_money VARCHAR2(20), -- 희망연봉 
   CONSTRAINT PK_TBL_PAPER_PAPER_CODE PRIMARY KEY (paper_code),   --   이력서 코드 PK 지정
   CONSTRAINT FK_TBL_PAPER_FK_USER_ID FOREIGN KEY (fk_user_id) REFERENCES TBL_USER_INFO (user_id),   --   개인아이디 FK 지정
   CONSTRAINT FK_TBL_PAPER_FK_LICENSE_CODE FOREIGN KEY (fk_license_code) REFERENCES TBL_LICENSE_DETAIL (license_code), --   자격증코드 FK 지정
   CONSTRAINT FK_TBL_PAPER_FK_LOCAL_CODE FOREIGN KEY (fk_local_code) REFERENCES TBL_LOCAL(local_code)   --   지역 코드 FK 지정
    );
    -- Table TBL_PAPER이(가) 생성되었습니다.

    

    -- 업종
    create table tbl_jobtype (
   jobtype_code NUMBER(10) NOT NULL -- 업종코드
   , jobtype_name NVARCHAR2(20) NOT NULL -- 업종명
   , constraint PK_tbl_jobtype_jobtype_code primary key(jobtype_code)
        -- jobtype_code P.K 지정
    );
    -- Table TBL_JOBTYPE이(가) 생성되었습니다.


    -- 기업
    create table tbl_company 
    (company_id VARCHAR2(10) NOT NULL           -- 기업아이디
    ,company_passwd VARCHAR2(20) NOT NULL       -- 비밀번호
    ,company_name NVARCHAR2(30) NOT NULL        -- 기업명
    ,businees_number VARCHAR2(20) NOT NULL      -- 사업자등록번호
    ,company_type NVARCHAR2(10) NOT NULL        -- 기업형태
    ,ceo_name NVARCHAR2(10) NOT NULL            -- 대표자명
    ,fk_jobtype_code NUMBER(10) NOT NULL        -- 업종코드
    ,fk_local_code NUMBER(3) NOT NULL           -- 지역코드
    ,company_address NVARCHAR2(100) NOT NULL    -- 주소
    ,CONSTRAINT PK_tbl_company_company_id primary key(company_id)   -- company_id PK 지정
    ,CONSTRAINT FK_tbl_company_fk_jobtype_code foreign key(fk_jobtype_code) references tbl_jobtype(jobtype_code) -- fk_jobtype_code FK 지정
    ,CONSTRAINT FK_tbl_company_fk_local_code foreign key(fk_local_code) references tbl_local(local_code)    -- fk_local_code FK 지정
    );
    -- Table TBL_COMPANY이(가) 생성되었습니다.



    -- 기업로그인 
    create table tbl_company_login 
    (fk_company_id VARCHAR2(10) NOT NULL    -- 기업아이디 
    ,company_passwd VARCHAR2(20) NOT NULL   -- 비밀번호 
    ,company_name NVARCHAR2(10) NOT NULL    -- 기업명 
    ,CONSTRAINT PK_company_login_fk_company_id primary key(fk_company_id) -- fk_company_id PK 지정
    ,CONSTRAINT FK_company_login_fk_company_id foreign key(fk_company_id) references tbl_company(company_id) -- fk_company_id FK 지정
    );
    -- Table TBL_COMPANY_LOGIN이(가) 생성되었습니다.


    -- 기업형태
    create table tbl_company_type 
    (fk_company_id VARCHAR2(10) NOT NULL    -- 기업아이디
    ,employee_num NUMBER(5) NOT NULL        -- 사원수 
    ,public_status VARCHAR2(1) DEFAULT 0 NOT NULL     -- 상장여부
    ,begin_day DATE NOT NULL                -- 설립일자 
    ,capital_money VARCHAR2(20) NOT NULL    -- 자본금 
    ,companylist_num NUMBER(3)              -- 계열회사수
    ,CONSTRAINT PK_company_type_fk_company_id primary key(fk_company_id)    -- fk_company_id PK 지정
    ,CONSTRAINT FK_company_type_fk_company_id foreign key(fk_company_id) references tbl_company(company_id) -- fk_company_id FK 지정
    ,CONSTRAINT CK_company_type_public_status check(public_status in(0,1))  -- public_status check 제약 지정
    );
    -- Table TBL_COMPANY_TYPE이(가) 생성되었습니다.


 -- tbl_local 지역별 값넣기
 
    create sequence seq_local_code
    start with 1 
    increment by 1 
    nomaxvalue
    nominvalue
    nocycle
    nocache;
    -- Sequence SEQ_LOCAL_CODE이(가) 생성되었습니다.
  
    -- TBL_LOCAL 지역별 값넣기
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'강남구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'강북구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'강서구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'광진구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'구로구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'노원구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'도봉구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'동작구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'마포구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'서초구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'성동구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'송파구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'양천구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'용산구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'은평구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'중구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'서울' ,'중랑구');
    
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'고양시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'과천시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'광명시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'구리시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'군포시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'남양주시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'동두천시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'부천시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'성남시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'수원시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'시흥시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'안산시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'안성시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'안양시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'여주시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'오산시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'용인시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'의정부시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'이천시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'평택시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'포천시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'경기' ,'화성시');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'인천' ,'남동구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'인천' ,'동구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'인천' ,'부평구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'인천' ,'서구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'인천' ,'중구');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'강서구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'남구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'동래구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'부산진구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'사상구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'사하구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'수영구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'연제구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'중구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'부산' ,'해운대구');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'대구' ,'달서구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'대구' ,'북구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'대구' ,'서구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'대구' ,'중구');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'광주' ,'광산구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'광주' ,'동구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'광주' ,'북구');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'대전' ,'대덕구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'대전' ,'서구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'대전' ,'유성구');
    
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'울산' ,'남구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'울산' ,'북구');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'울산' ,'울주군');
    
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval ,'세종' ,'세종특별자치시');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '강원' , '강릉시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '강원' , '동해시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '강원' , '삼척시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '강원' , '원주시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '강원' , '춘천시');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경남' , '거제시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경남' , '김해시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경남' , '밀양시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경남' , '사천시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경남' , '양산시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경남' , '창원시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경남' , '통영시');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경북' , '경산시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경북' , '구미시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경북' , '김천시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경북' , '문경시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경북' , '상주시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경북' , '영천시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '경북' , '포항시');
    
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '전남' , '광양시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '전남' , '나주시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '전남' , '여수시');
    
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '전북' , '김제시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '전북' , '남원시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '전북' , '익산시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '전북' , '전주시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '전북' , '정읍시');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '충남' , '계룡시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '충남' , '논산시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '충남' , '보령시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '충남' , '아산시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '충남' , '천안시');
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '충북' , '제천시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '충북' , '청주시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '충북' , '충주시');
    
    
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '제주' , '제주시');
    insert into tbl_local (local_code , local_name, city_name) values (seq_local_code.nextval , '제주' , '서귀포시');
    
    select * from tbl_local;
    
    
    
    -- 학력 테이블 값넣기
    insert into tbl_academy (academy_code, academy_name) values (1, '고등학교졸업미만');
    insert into tbl_academy (academy_code, academy_name) values (2, '고등학교졸업');
    insert into tbl_academy (academy_code, academy_name) values (3, '대학교 재학중');
    insert into tbl_academy (academy_code, academy_name) values (4, '대학교 졸업2년제');
    insert into tbl_academy (academy_code, academy_name) values (5, '대학교 졸업3년제');
    insert into tbl_academy (academy_code, academy_name) values (6, '대학교 졸업4년제');
    insert into tbl_academy (academy_code, academy_name) values (7, '대학원 석사 졸업');
    insert into tbl_academy (academy_code, academy_name) values (8, '대학원 박사 졸업');
    select * from tbl_academy;
    -- 학력 값넣기 완료
    
    
    -- 취업우대 테이블 값넣기
    insert into tbl_priority (priority_code , priority_name) values (1 , '컴퓨터활용능력 우수');
    insert into tbl_priority (priority_code , priority_name) values (2 , '국가유공자');
    insert into tbl_priority (priority_code , priority_name) values (3 , '보훈대상자');
    insert into tbl_priority (priority_code , priority_name) values (4 , '고용촉진지원금 대상');
    insert into tbl_priority (priority_code , priority_name) values (5 , '취업보호대상자');
    insert into tbl_priority (priority_code , priority_name) values (6 , '병역특례');
    insert into tbl_priority (priority_code , priority_name) values (7 , '공모전입상자');
    insert into tbl_priority (priority_code , priority_name) values (8 , '외국어가능자');
    insert into tbl_priority (priority_code , priority_name) values (9 , '인근거주자');
    -- 채용우대혜택 값넣기 완료
    commit;
    select * from tbl_priority;
    
=======
    -- 고용형태(채용공고일련번호 제외)
    CREATE TABLE TBL_HIRETYPE ( -- 고용형태 테이블
   hiretype_code VARCHAR2(10) NOT NULL -- 고용형태코드 
   ,fk_recruit_no VARCHAR2(20) NOT NULL -- 채용공고일련번호
   ,hiretype_name NVARCHAR2(10) NOT NULL -- 고용형태명
   ,CONSTRAINT PK_TBL_HIRETYPE_HIRETYPE_CODE PRIMARY KEY(HIRETYPE_CODE) -- HIRETYPE_CODE PK지정
       );
       
    -- 고용형태 값넣기 
   insert into tbl_hiretype (hiretype_code , hiretype_name) values (1 , '정규직');
   insert into tbl_hiretype (hiretype_code , hiretype_name) values (2 , '계약직');
   insert into tbl_hiretype (hiretype_code , hiretype_name) values (3 , '인턴');
   insert into tbl_hiretype (hiretype_code , hiretype_name) values (4 , '파견직');
   insert into tbl_hiretype (hiretype_code , hiretype_name) values (5 , '프리랜서');
   insert into tbl_hiretype (hiretype_code , hiretype_name) values (6 , '아르바이트');
   insert into tbl_hiretype (hiretype_code , hiretype_name) values (7 , '연수생');
   insert into tbl_hiretype (hiretype_code , hiretype_name) values (8 , '위촉직');
   insert into tbl_hiretype (hiretype_code , hiretype_name) values (9 , '개인사업자');
   commit;
   -- 고용형태 값넣기 완료
   select * from tbl_hiretype;
   
   
       
    select * from tbl_hiretype;

    -- 채용공고
    CREATE TABLE TBL_RECRUIT_INFO ( -- 채용공고 테이블
   recruit_no VARCHAR2(20) NOT NULL -- 채용공고일련번호 
   ,fk_company_id VARCHAR2(10) NOT NULL -- 기업아이디 
   ,fk_hiretype_code VARCHAR2(10) NOT NULL -- 고용형태코드 
   ,manager_email VARCHAR2(30) NOT NULL -- 채용담당자이메일 
   ,manager_name NVARCHAR2(10) NOT NULL -- 채용담당자명 
   ,recruit_title NVARCHAR2(20) NOT NULL -- 채용공고명 
   ,recruit_registerday DATE DEFAULT sysdate NOT NULL -- 채용등록일 
   ,recruit_deadline DATE NOT NULL -- 채용마감일 
   ,career NVARCHAR2(5) NOT NULL -- 신입/경력 여부 
   ,year_salary VARCHAR2(20) -- 연봉 
   ,recruit_people NUMBER(3) -- 채용인원 
   ,recruit_content NVARCHAR2(2000) NOT NULL -- 채용공고내용
   ,recruit_field NVARCHAR2(20) NOT NULL -- 채용분야 
   ,work_day NVARCHAR2(10) NOT NULL -- 근무요일 
   ,work_time NVARCHAR2(20) NOT NULL -- 근무시간
   ,CONSTRAINT PK_RC_INFO_RECRUIT_NO PRIMARY KEY(RECRUIT_NO) -- RECRUIT_NO PK지정
   ,CONSTRAINT FK_RC_INFO_FK_COMPANY_ID FOREIGN KEY(FK_COMPANY_ID) REFERENCES TBL_COMPANY(COMPANY_ID) -- FK_COMPANY_ID FK지정
   ,CONSTRAINT FK_RC_INFO_FK_HIRETYPE_CODE FOREIGN KEY(FK_HIRETYPE_CODE) REFERENCES TBL_HIRETYPE(HIRETYPE_CODE) -- FK_HIRETYPE FK지정
   ,CONSTRAINT UQ_RC_INFO_MANAGER_EMAIL UNIQUE(MANAGER_EMAIL) -- MANAGER_EMAIL UK지정
    ); 
    -- Table TBL_RECRUIT_INFO이(가) 생성되었습니다.


    

   -- 채용지원
    create table tbl_recruit_apply (
   fk_recruit_no VARCHAR2(20) NOT NULL -- 채용공고일련번호
   , fk_paper_code NUMBER(6) NOT NULL -- 이력서코드
   , apply_motive NVARCHAR2(300) NOT NULL -- 지원동기 
   , apply_day DATE DEFAULT sysdate NOT NULL -- 지원일자 
   , success_status NUMBER(1) DEFAULT 0 NOT NULL -- 합격여부 
   , constraint FK_rc_apply_fk_recruit_no foreign key(fk_recruit_no) references tbl_recruit_info(recruit_no)   -- fk_recruit_no F.K 지정
   , constraint FK_rc_apply_fk_paper_code foreign key(fk_paper_code) references tbl_paper(paper_code)    
   , constraint CK_rc_apply_success_status check(success_status in(0,1))   -- success_status check 제약 지정
   , constraint PK_rc_apply_rc_no_paper_code primary key(fk_recruit_no,fk_paper_code) -- 복합 pk 지정
       
    );
   -- Table TBL_RECRUIT_APPLY이(가) 생성되었습니다.
   
   
   -- 자격증상세등록 테이블 유니크키 해제
   select * from tbl_license_detail;   
   
   select A.table_name, A.constraint_name, A.constraint_type, A.search_condition
         , B.column_name, B.position 
    from user_constraints A
    join user_cons_columns B
    on A.constraint_name = B.constraint_name
    where A.constraint_type in('U','R'); 
   
   alter table tbl_license_detail
   drop constraints UK_LICENSE_DETAIL_LICENSE_NAME; 
   
   
   -- 고용형태 테이블 외래키 제거 및 컬럼제거
   alter table tbl_HIRETYPE
   drop constraints FK_TBL_HIRETYPE_FK_RECRUIT_NO;
   
   select * from tbl_hiretype;
   
  alter table tbl_HIRETYPE
  drop column fk_recruit_no;
  -- 완료
  
    alter table TBL_USER_INFO
    add status number(1);
    
    alter table TBL_COMPANY
    add status number(1);
  
  /*
    [테이블 null값 허용으로 변경_20240314]
    fk_local_code
    fk_jobtype_code
    company_type
    
    // 참고
    명령어 : alter table 테이블명 modify (컬럼명 null);
*/
    
    ----------------------------------------------------------------------------------
    ----------------------------------------------------------------------------------
    ----------------------------------------------------------------------------------
    
    -- 테이블 데이터타입 조회
   select table_name  , column_name  , data_type  , 
          data_length  , nullable  , data_default 
    from USER_TAB_COLUMNS;
    
   
    alter table tbl_company drop column company_type; -- 기업테이블의 기업형태 컬럼 삭제
    -- Table TBL_COMPANY이(가) 변경되었습니다.
    
    alter table tbl_company_type add company_type Nvarchar2(10); -- 기업형태 테이블의 기업형태명 컬럼 추가
    -- Table TBL_COMPANY_TYPE이(가) 변경되었습니다.
    
    ALTER TABLE tbl_company MODIFY status number(1) DEFAULT 1; -- 기업테이블 가입구분 기본값 1로 세팅
    -- Table TBL_COMPANY이(가) 변경되었습니다.
    
    ALTER TABLE tbl_user_info MODIFY status number(1) DEFAULT 1; -- 구직자테이블 가입구분 기본값 1로 세팅
    -- Table TBL_USER_INFO이(가) 변경되었습니다.
    
    select * from tbl_user_info;
/*  
    user_id 가 admin , test4 , 2 , leess	 이미 만들어진 테스트 계정이라 status가 null, update하거나 drop하거나 해야합니다 by 정수		
*/    
    
    select * from tbl_company;
/*  
    company_id 가 sa , 1	 이미 만들어진 테스트 계정이라 status가 null, update하거나 drop하거나 해야합니다 by 정수		
*/   
    commit;
    -- 커밋 완료.
    ----------------------------------------------------------------------------------
    ----------------------------------------------------------------------------------
    ----------------------------------------------------------------------------------

    
    delete from tbl_user_info where user_id = '3';
    delete from tbl_user_info where user_id = '4';
    delete from tbl_user_info where user_id = 'testtt';
    delete from tbl_user_info where user_id = 'hemint';
    delete from tbl_user_info where user_id = 'qw--';
    delete from tbl_user_info where user_id = 'qwet';
    delete from tbl_user_info where user_id = '2345';
    delete from tbl_user_info where user_id = 'test2';
    delete from tbl_user_info where user_id = '5';
    delete from tbl_user_info where user_id = 'aq_2';
    delete from tbl_user_info where user_id = 'admin1';
    delete from tbl_user_info where user_id = 'admin';
    delete from tbl_user_info where user_id = 'test4';
    delete from tbl_user_info where user_id = '2';
    delete from tbl_user_info where user_id = '1';
    delete from tbl_user_info where user_id = 'leess';
    delete from tbl_user_info where user_id = 'qwer';
    delete from tbl_user_info where user_id = 'ㅈㄷㄳ';
    delete from tbl_user_info where user_id = 'jjoung-2j';
    -- 구직자 불필요한 회원 전부 삭제
    select * from tbl_user_info;
    commit;
    -- 커밋 완료.
    
    select table_name  , column_name  , data_type  , 
          data_length  , nullable  , data_default 
    from USER_TAB_COLUMNS
    where table_name = 'TBL_RECRUIT_INFO';
     
   select * from user_sequences;
   select * from TBL_RECRUIT_INFO;
   
   alter table tbl_recruit_info modify recruit_deadline date;
   -- Table TBL_RECRUIT_INFO이(가) 변경되었습니다.
   
   -- 기업테이블 company_id(기업아이디 lg,hd,samsung) 제외하고 전부 삭제 by 정수(20240317 00시07분경)
   
 ---------------------------------------------------------------------------------------
   -- 채용공고 테이블 마감일 미입력시 채용마감시까지로 하기위해 변경 및 삭제
   delete from tbl_recruit_info;
   -- 4개 행 이(가) 삭제되었습니다.
   
   alter table tbl_recruit_info modify recruit_deadline Nvarchar2(20);
   -- Table TBL_RECRUIT_INFO이(가) 변경되었습니다.
   
   commit;
   -- 커밋 완료.
   
   -- user_name , ceo_name , manager_name 현실성 첨가해서 37글자 제한으로 변경
   alter table tbl_company modify ceo_name Nvarchar2(37);
   alter table tbl_user_info modify user_name Nvarchar2(37);
   alter table tbl_recruit_info modify manager_name Nvarchar2(37);
   -- Table TBL_COMPANY이(가) 변경되었습니다.
   -- Table TBL_USER_INFO이(가) 변경되었습니다.
   -- Table TBL_RECRUIT_INFO이(가) 변경되었습니다. by 정수
   
  
   
  
    with
   A as
   (
   select academy_name , academy_code , fk_academy_code ,user_id
   from tbl_academy A join tbl_user_info U
   on A.academy_code = U.fk_academy_code
   -- 학력과 유저
   )
   ,
   R as
   (
   select priority_name , priority_code , fk_priority_code , user_id
   from tbl_priority P join tbl_user_info U
   on P.priority_code = U.fk_priority_code
   -- 취업우대와 유저
   )
   ,
   U as
   (
   select user_name, user_tel, user_address, user_email , user_id , 
   fk_user_id , career , fk_local_code, fk_license_code , paper_code ,paper_name
   from tbl_user_info U join tbl_paper P
   on U.user_id = P.fk_user_id
   -- 유저정보와 이력서
   )
   ,
   D as
   (
   select license_name , license_day, license_company , license_code , fk_license_code
   from tbl_license_detail join tbl_paper
   on license_code = fk_license_code
   -- 자격증과 이력서
   )
   , Y as
   (
   select local_code, fk_local_code , local_name, city_name
   from tbl_local join tbl_paper
   on local_code = fk_local_code
   ) -- 지역과 이력서
   , Q as
   (
   select fk_paper_code , fk_recruit_no ,paper_code , apply_motive , apply_day
   from tbl_recruit_apply N join tbl_paper G
   on N.fk_paper_code = G.paper_code
   ) -- 이력서와 채용지원
   select Q.fk_recruit_no , Q.paper_code , U.paper_name, U.user_name , U.user_tel, U.user_address, U.user_email ,
   A.academy_name , R.priority_name , local_name, city_name , U.career , license_name , license_day,
   license_company , Q.apply_motive , Q.apply_day
   from A join R
   on A.user_id = R.user_id join U
   on R.user_id = U.user_id left join D
   on U.fk_license_code = D.fk_license_code join Y
   on U.fk_local_code = Y.local_code join Q
   on U.paper_code = Q.fk_paper_code;
   

    -- 채용공고 해제
alter table tbl_recruit_info
drop constraint FK_RC_INFO_FK_COMPANY_ID;
--Table TBL_RECRUIT_INFO이(가) 변경되었습니다.

alter table tbl_recruit_info
add constraint FK_RC_INFO_FK_COMPANY_ID
FOREIGN KEY(FK_COMPANY_ID) REFERENCES TBL_COMPANY(COMPANY_ID) on delete cascade;
-- Table TBL_RECRUIT_INFO이(가) 변경되었습니다.

-- 기업로그인 해제
alter table tbl_company_login
drop constraint fk_company_login_fk_company_id;
-- Table TBL_COMPANY_LOGIN이(가) 변경되었습니다.


alter table tbl_company_login
add constraint fk_company_login_fk_company_id
FOREIGN KEY(FK_COMPANY_ID) REFERENCES TBL_COMPANY(COMPANY_ID) on delete cascade;
-- Table TBL_COMPANY_LOGIN이(가) 변경되었습니다.

-- 기업형태 해제
alter table tbl_company_type
drop constraint fk_company_type_fk_company_id;
-- Table TBL_COMPANY_TYPE이(가) 변경되었습니다.


alter table tbl_company_type
add constraint fk_company_type_fk_company_id
FOREIGN KEY(FK_COMPANY_ID) REFERENCES TBL_COMPANY(COMPANY_ID) on delete cascade;
-- Table TBL_COMPANY_TYPE이(가) 변경되었습니다.

-- 채용지원 on delete cascade 추가
alter table tbl_recruit_apply
drop constraint fk_rc_apply_fk_recruit_no;
-- Table TBL_COMPANY_TYPE이(가) 변경되었습니다.


alter table tbl_recruit_apply
add constraint fk_rc_apply_fk_recruit_no
FOREIGN KEY(fk_recruit_no) REFERENCES tbl_recruit_info(recruit_no) on delete cascade;

select *
from tbl_company;

select *
from tbl_jobtype;


select B.recruit_no
, A.company_name, A.company_address, B.career, B.year_salary
, '~' || B.recruit_deadline AS recruit_deadline
from TBL_COMPANY A RIGHT JOIN TBL_RECRUIT_INFO B
ON A.company_id = B.fk_company_id
order by recruit_no desc;


    with
    P as
    (
        select *
        from tbl_paper
    )
    ,
    U as
    (
        select *
        from tbl_user_info
    )
    select U.user_id, P.paper_code
    from P JOIN U
    ON P.fk_user_id = U.user_id
    where U.user_id = 'test2' and paper_code = '20';
    
    
   
   
    with 
    A as 	
    ( 
        select academy_name, user_id 
        from tbl_academy A join tbl_user_info U 
        on A.academy_code = U.fk_academy_code 
    ) 
    , R as 	
    ( 
        select priority_name, user_id 
        from tbl_priority P join tbl_user_info U 
        on P.priority_code = U.fk_priority_code 
    ) 
    , 
    U as 
    ( 	
        select career, paper_name, user_id, fk_license_code, paper_code 
        from tbl_user_info U join tbl_paper P 
        on U.user_id = P.fk_user_id 
    ) 
    , 
    D as 
    ( 	
        select license_name, fk_license_code 
        from tbl_license_detail join tbl_paper 
        on license_code = fk_license_code 
    ) 
    , Q as 
    ( 	
        select paper_code , fk_recruit_no, apply_motive , apply_day 
        from tbl_recruit_apply N join tbl_paper G 
        on N.fk_paper_code = G.paper_code 
    )  	
    select  Q.fk_recruit_no , U.career ,Q.apply_motive, Q.paper_code , U.paper_name 
        , A.academy_name , R.priority_name , nvl(license_name, ' ') as 취업우대 
        , Q.apply_day 
    from A join R 
    on A.user_id = R.user_id join U 
    on R.user_id = U.user_id left join D 
    on U.fk_license_code = D.fk_license_code join Q 
    on U.paper_code = Q.paper_code
    where user_id = 'ttest';
   
   alter table TBL_COMPANY
    modify CEO_NAME NVARCHAR2(40);
    
    alter table TBL_RECRUIT_INFO
    modify MANAGER_NAME NVARCHAR2(40);
    
    drop table tbl_user_login purge;
    
    commit;
    
    
    with
	A as
	( 
	select academy_name, user_id 
	from tbl_academy A join tbl_user_info U 
	on A.academy_code = U.fk_academy_code 
	) 
	, R as
    ( 
	select priority_name, user_id 
	from tbl_priority P join tbl_user_info U 
	on P.priority_code = U.fk_priority_code 
	) 
	, 
	 U as 
	( 
	select career, paper_name, user_id, fk_license_code, paper_code 
	from tbl_user_info U join tbl_paper P 
	on U.user_id = P.fk_user_id 
	) 
	, 
	D as 
	( 
	select license_name, fk_license_code 
	from tbl_license_detail join tbl_paper 
	on license_code = fk_license_code 
	) 
	, Q as 
	( 
    select paper_code , fk_recruit_no, apply_motive , apply_day 
	from tbl_recruit_apply N join tbl_paper G 
	on N.fk_paper_code = G.paper_code 
	)
	select  Q.fk_recruit_no , U.career ,Q.apply_motive, Q.paper_code , U.paper_name 
	, A.academy_name , R.priority_name , nvl(license_name, ' ') as 취업우대 
    , Q.apply_day 
    from A join R 
	on A.user_id = R.user_id join U 
	on R.user_id = U.user_id left join D 
	on U.fk_license_code = D.fk_license_code join Q 
	on U.paper_code = Q.paper_code;
    
    
	where U.user_id = ? ;
    
    