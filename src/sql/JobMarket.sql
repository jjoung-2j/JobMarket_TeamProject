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



<<<<<<< HEAD
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
    select * from tbl_academy
    
    
    commit;
    
    -- 취업우대 테이블 값넣기
    insert into tbl_priority (priority_code , priority_name) values (1 , '컴퓨터활용능력 우수자');
    insert into tbl_priority (priority_code , priority_name) values (2 , '국가유공자');
    insert into tbl_priority (priority_code , priority_name) values (3 , '보훈대상자');
    insert into tbl_priority (priority_code , priority_name) values (4 , '고용촉진지원금 대상자');
    insert into tbl_priority (priority_code , priority_name) values (5 , '취업보호대상자');
    insert into tbl_priority (priority_code , priority_name) values (6 , '병역특례');
    insert into tbl_priority (priority_code , priority_name) values (7 , '공모전입상자');
    insert into tbl_priority (priority_code , priority_name) values (8 , '외국어가능자');
    insert into tbl_priority (priority_code , priority_name) values (9 , '인근거주자');
=======
    -- 고용형태(채용공고일련번호 제외)
    CREATE TABLE TBL_HIRETYPE ( -- 고용형태 테이블
   hiretype_code VARCHAR2(10) NOT NULL -- 고용형태코드 
   ,fk_recruit_no VARCHAR2(20) NOT NULL -- 채용공고일련번호
   ,hiretype_name NVARCHAR2(10) NOT NULL -- 고용형태명
   ,CONSTRAINT PK_TBL_HIRETYPE_HIRETYPE_CODE PRIMARY KEY(HIRETYPE_CODE) -- HIRETYPE_CODE PK지정
       );
       -- 


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


    -- 채용공고테이블까지 생성완료되었다면,
    ALTER TABLE TBL_HIRETYPE
    ADD CONSTRAINT FK_TBL_HIRETYPE_FK_RECRUIT_NO FOREIGN KEY(FK_RECRUIT_NO) REFERENCES TBL_RECRUIT_INFO(RECRUIT_NO);
    -- Table TBL_HIRETYPE이(가) 변경되었습니다.

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
   
>>>>>>> develop
