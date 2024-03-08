    -- 오라클 계정 생성하기
    -- >> SYS 연결 <<
    
    alter session set "_ORACLE_SCRIPT" = true;
    
    -- 계정명 JOB_MARKET 이고 암호는 gclass 인 사용자 계정 생성
    create user JOB_MARKET identified by gclass default tablespace users;
    
    -- 권한 주기
    grant connect, resource, create view, unlimited tablespace to JOB_MARKET;
    
    -- >> 