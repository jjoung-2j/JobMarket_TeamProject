    show user;
    -- USER이(가) "MINI_ORAUSER1"입니다.
   
    
  select * from tab;  
  
  
 /* 구직자로그인 */
CREATE table USER_LOGIN (
	fk_user_id VARCHAR2(10) NOT NULL, /* 개인아이디 */
	user_passwd VARCHAR2(20) NOT NULL, /* 비밀번호 */
	user_name NVARCHAR2(10) NOT NULL /* 성명 */
);

/* 기업 로그인 */
CREATE TABLE COMPANY_LOGIN (
	fk_company_id VARCHAR2(10) NOT NULL, /* 기업아이디 */
	company_passwd VARCHAR2(20) NOT NULL, /* 비밀번호 */
	company_name NVARCHAR2(10) NOT NULL /* 기업명 */
);

/* 구직자 */
CREATE TABLE USER_INFO (
	user_id VARCHAR2(10) NOT NULL, /* 개인아이디 */
	user_passwd VARCHAR2(20) NOT NULL, /* 비밀번호 */
	user_name NVARCHAR2(10) NOT NULL, /* 성명 */
	user_address NVARCHAR2(100), /* 주소 */
	user_tel VARCHAR2(15) NOT NULL, /* 연락처 */
	user_security_num VARCHAR2(20) NOT NULL, /* 주민번호 */
	user_email VARCHAR2(30) NOT NULL, /* 이메일 */
	fk_priority_code NUMBER(1), /* 취업우대코드 */
	fk_academy_code NUMBER(1) /* 학력코드 */
);

/* 기업 */
CREATE TABLE COMPANY (
	company_id VARCHAR2(10) NOT NULL, /* 기업아이디 */
	company_passwd VARCHAR2(20) NOT NULL, /* 비밀번호 */
	company_name NVARCHAR2(30) NOT NULL, /* 기업명 */
	businees_number VARCHAR2(20) NOT NULL, /* 사업자등록번호 */
	company_type NVARCHAR2(10) NOT NULL, /* 기업형태 */
	ceo_name NVARCHAR2(10) NOT NULL, /* 대표자명 */
	fk_jobtype_code NUMBER(10) NOT NULL, /* 업종코드 */
	fk_local_code NUMBER(3) NOT NULL, /* 지역코드 */
	company_address NVARCHAR2(100) NOT NULL /* 주소 */
);

/* 채용 공고 */
CREATE TABLE RECRUIT_INFO (
	recruit_no VARCHAR2(20) NOT NULL, /* 채용공고일련번호 */
	fk_company_id VARCHAR2(10) NOT NULL, /* 기업아이디 */
	fk_hiretype_code VARCHAR2(10) NOT NULL, /* 고용형태코드 */
	manager_email VARCHAR2(30) NOT NULL, /* 채용담당자이메일 */
	manager_name NVARCHAR2(10) NOT NULL, /* 채용담당자명 */
	recruit_title NVARCHAR2(20) NOT NULL, /* 채용공고명 */
	recruit_registerday DATE DEFAULT sysdate NOT NULL, /* 채용등록일 */
	recruit_deadline DATE NOT NULL, /* 채용마감일 */
	career NVARCHAR2(5) NOT NULL, /* 신입/경력 여부 */
	year_salary VARCHAR2(20), /* 연봉 */
	recruit_people NUMBER(3), /* 채용인원 */
	recruit_content NVARCHAR2(2000) NOT NULL, /* 채용공고내용 */
	recruit_field NVARCHAR2(20) NOT NULL, /* 채용분야 */
	work_day NVARCHAR2(10) NOT NULL, /* 근무요일 */
	work_time NVARCHAR2(20) NOT NULL /* 근무시간 */
);

/* 채용지원 */
CREATE TABLE RECRUIT_APPLY (
	fk_recruit_no VARCHAR2(20) NOT NULL, /* 채용공고일련번호 */
	fk_paper_code NUMBER(6) NOT NULL, /* 이력서코드 */
	apply_motive NVARCHAR2(300) NOT NULL, /* 지원동기 */
	apply_day DATE DEFAULT sysdate NOT NULL, /* 지원일자 */
	COL NUMBER(1) DEFAULT 0 NOT NULL /* 합격여부 */
);

/* 이력서 */
CREATE TABLE PAPER (
	paper_code NUMBER(6) NOT NULL, /* 이력서코드 */
	fk_user_id VARCHAR2(10) NOT NULL, /* 개인아이디 */
	fk_license_code NVARCHAR2(20), /* 자격증코드 */
	fk_local_code NUMBER(3) NOT NULL, /* 지역코드 */
	user_security_num VARCHAR2(20), /* 주민번호(역정) */
	career NVARCHAR2(5) NOT NULL, /* 신입/경력 여부 */
	hope_money VARCHAR2(20) /* 희망연봉 */
);

/* 기업형태 */
CREATE TABLE COMPANY_TYPE (
	fk_company_id VARCHAR2(10) NOT NULL, /* 기업아이디 */
	employee_num NUMBER(5) NOT NULL, /* 사원수 */
	public_status VARCHAR2(1) NOT NULL, /* 상장여부 */
	begin_day DATE NOT NULL, /* 설립일자 */
	capital_money VARCHAR2(20) NOT NULL, /* 자본금 */
	companylist_num NUMBER(3) /* 계열회사수 */
);

/* 고용형태 */
CREATE TABLE HIRETYPE (
	hiretype_code VARCHAR2(10) NOT NULL, /* 고용형태코드 */
	fk_recruit_no VARCHAR2(20) NOT NULL, /* 채용공고일련번호 */
	hiretype_name NVARCHAR2(10) NOT NULL /* 고용형태명 */
);

/* 자격증상세등록 */
CREATE TABLE LlCENSE_DETAIL (
	license_code NVARCHAR2(20) NOT NULL, /* 자격증코드 */
	license_name NVARCHAR2(15) NOT NULL, /* 자격증명 */
	license_day DATE NOT NULL, /* 취득일자 */
	license_company NVARCHAR2(20) NOT NULL /* 발급기관 */
);

/* 지역 */
CREATE TABLE LOCAL (
	local_code NUMBER(3) NOT NULL, /* 지역코드 */
	local_name NVARCHAR2(10) NOT NULL, /* 지역명 */
	city_name NVARCHAR2(10) NOT NULL /* 도시명 */
);

/* 업종 */
CREATE TABLE JOBTYPE (
	jobtype_code NUMBER(10) NOT NULL, /* 업종코드 */
	jobtype_name NVARCHAR2(20) NOT NULL /* 업종명 */
);

/* 취업우대 */
CREATE TABLE PRIORITY (
	priority_code NUMBER(1) NOT NULL, /* 취업우대코드 */
	priority_name NVARCHAR2(10) /* 취업우대명 */
);

/* 학력 */
CREATE TABLE ACADEMY (
	academy_code NUMBER(1) NOT NULL, /* 학력코드 */
	academy_name NVARCHAR2(10) NOT NULL /* 학력명 */
);


    