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
   