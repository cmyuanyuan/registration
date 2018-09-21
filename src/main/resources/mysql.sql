-- Hospital for MySQL ------------------------------
-- by SONG 2017-12-28 ------------------------------

-- create database if not exists hospital;

-- ����ԤԼ���˱�
CREATE TABLE patients (
  pid     BIGINT AUTO_INCREMENT       COMMENT 'id',
  pname   VARCHAR(60)        NOT NULL COMMENT '����',
  gender  VARCHAR(1)         NOT NULL COMMENT '�Ա�',
  phone   VARCHAR(11) UNIQUE NOT NULL COMMENT '�ֻ�����', -- �����ֻ���������֤�ŵ�¼
  idcard  VARCHAR(20) UNIQUE NOT NULL COMMENT '���֤', -- ������Ը������֤�ż���
  pwd     VARCHAR(20)        NOT NULL COMMENT '����',
  medcard BIGINT COMMENT '���ƿ���',
  email   VARCHAR(40) COMMENT 'EMAIL',
  by1     VARCHAR(30) COMMENT '����',
  by2     BIGINT COMMENT '����2',
  CONSTRAINT pk_patients PRIMARY KEY (pid)
);

-- ���ƿ�
CREATE TABLE cards (
  cid       BIGINT AUTO_INCREMENT  COMMENT 'id',
  pname     VARCHAR(60)   NOT NULL COMMENT '����',
  gender    VARCHAR(1)    NOT NULL COMMENT '�Ա�',
  phone     VARCHAR(20)   NOT NULL COMMENT '�ֻ�����', -- �����ֻ���������֤�ŵ�¼
  idcard    VARCHAR(20)   NOT NULL COMMENT '���֤',
  pwd       VARCHAR(20)   NOT NULL COMMENT '����',
  ramaining DOUBLE COMMENT '���',
  newdate   DATE COMMENT '����ʱ��',
  -- pid BIGINT references patients(pid),--���˵����
  doexist   INT DEFAULT 1 NOT NULL COMMENT '0Ϊͣ�� 1Ϊ����',
  by1       VARCHAR(30) COMMENT '����',
  by2       BIGINT COMMENT '����2',
  CONSTRAINT pk_cards PRIMARY KEY (cid)
);

-- ���ұ�
CREATE TABLE departs (
  deid    BIGINT AUTO_INCREMENT       COMMENT 'id',
  dename  VARCHAR(60) UNIQUE NOT NULL COMMENT '������',
  intro   VARCHAR(100) COMMENT '���', 
  deexist INT DEFAULT 1      NOT NULL COMMENT '0Ϊͣ�� 1Ϊ����',
  by1     VARCHAR(30) COMMENT '����',
  by2     BIGINT COMMENT '����2',
  CONSTRAINT pk_departs PRIMARY KEY (deid)
);

-- ҽ����
CREATE TABLE doctors (
  doid    BIGINT AUTO_INCREMENT  COMMENT 'id',
  doname  VARCHAR(60)   NOT NULL COMMENT 'ҽ����',
  title   VARCHAR(60)   NOT NULL COMMENT 'ҽ��ְ��',
  photo   VARCHAR(100) COMMENT '��Ƭ',
  info    VARCHAR(90) COMMENT '��Ϣ����',
  deid    BIGINT COMMENT '����id���' REFERENCES departs (deid),
  monam   INT COMMENT '��һ�����Ƿ�ֵ��,1Ϊ��',
  monpm   INT COMMENT '��һ�����Ƿ�ֵ��,1Ϊ��',
  tueam   INT COMMENT '�ܶ�����',
  tuepm   INT COMMENT '�ܶ�����',
  wedam   INT COMMENT '��������',
  wedpm   INT COMMENT '��������',
  thuam   INT COMMENT '��������',
  thupm   INT COMMENT '��������',
  friam   INT COMMENT '��������',
  fripm   INT COMMENT '��������',
  satam   INT COMMENT '��������',
  satpm   INT COMMENT '��������',
  sunam   INT COMMENT '��������',
  sumpm   INT COMMENT '��������',
  -- ÿСʱ��ԤԼ����(���Ͽ�ԤԼ��������,�ֳ���ԤԼ��������)
  pcreg   INT COMMENT '���Ͽ�ԤԼ����',
  xcreg   INT COMMENT '�ֳ���ԤԼ����',
  doexist INT DEFAULT 1 NOT NULL COMMENT '0Ϊͣ�� 1Ϊ����',
  bcost   DOUBLE        NOT NULL COMMENT '��ҽ������Һŷ�',
  by1     VARCHAR(30) COMMENT '����',
  by2     BIGINT COMMENT '����2',
  CONSTRAINT pk_doctors PRIMARY KEY (doid)
);

-- ��ԤԼ�Ű��
CREATE TABLE bookable (
  bid       BIGINT AUTO_INCREMENT COMMENT 'id',
  doid      BIGINT COMMENT 'ҽ��id���' REFERENCES doctors (doid),
  bdate     DATETIME NOT NULL COMMENT '�Ű�����',
  starttime INT  NOT NULL COMMENT 'ʱ���(0Ϊ���� 1Ϊ����)', 
  used      INT  NOT NULL COMMENT '�Ƿ�����,0Ϊ�� 1Ϊ��',
  bnum      INT  NOT NULL COMMENT '���Ͽ�ԤԼ����',
  ynum      INT  NOT NULL COMMENT '������ԤԼ����',
  xcum      INT  NOT NULL COMMENT '�ֳ���ԤԼ����',
  xcyum     INT  NOT NULL COMMENT '�ֳ���ԤԼ����',
  by1       VARCHAR(30) COMMENT '����',
  by2       BIGINT COMMENT '����2',
  CONSTRAINT pk_bookable PRIMARY KEY (bid)
);

-- ����ԤԼ������ǰ-�Һŵ���
CREATE TABLE reservation (
  red   BIGINT AUTO_INCREMENT COMMENT 'id',
  pid   BIGINT COMMENT '����ԤԼ����id���' REFERENCES patients (pid),
  bid   BIGINT COMMENT 'ԤԼ�Ű�id���' REFERENCES bookable (bid),
  state INT COMMENT '״̬', -- ���˺�ʱ��
  by1   VARCHAR(30) COMMENT '����',
  by2   BIGINT COMMENT '����2',
  CONSTRAINT pk_reservation PRIMARY KEY (red)
);

-- �Һŵ���
CREATE TABLE registration (
  rid   BIGINT AUTO_INCREMENT COMMENT 'id',
  cid   BIGINT COMMENT '���ƿ�id���' REFERENCES cards (cid),
  bid   BIGINT COMMENT 'ԤԼ�Ű�id���' REFERENCES bookable (bid),
  snum  INT COMMENT 'Ʊ��',
  state INT COMMENT '״̬(�Ƿ���)',
  by1   VARCHAR(30) COMMENT '����',
  by2   BIGINT COMMENT '����2',
  CONSTRAINT pk_registration PRIMARY KEY (rid)
);

-- ȡ�ű�
CREATE TABLE takeble (
  tid  BIGINT AUTO_INCREMENT COMMENT 'id',
  rid  BIGINT COMMENT '�Һŵ���id���' REFERENCES registration (rid),
  snum INT COMMENT 'Ʊ��',
  by1  VARCHAR(30) COMMENT '����',
  by2  BIGINT COMMENT '����2',
  CONSTRAINT pk_takeble PRIMARY KEY (tid)
);

-- ��ɫ��
CREATE TABLE AUTHORITY (
  id    BIGINT COMMENT 'id',
  name  VARCHAR(50),
  descr VARCHAR(50),
  by1    VARCHAR(30) COMMENT '����',
  by2    BIGINT COMMENT '����2',
  CONSTRAINT pk_role PRIMARY KEY (id)
);

-- ����Ա��
CREATE TABLE admins (
  aid    BIGINT AUTO_INCREMENT       COMMENT 'id',
  aname  VARCHAR(50) UNIQUE NOT NULL COMMENT '��¼�û���',
  pwd    VARCHAR(100)        NOT NULL COMMENT '����',
  state  INT                NOT NULL COMMENT '��ɫ�ȼ� 1Ϊ��ͨ����Ա 0Ϊ��������Ա [��Ӧ��ɫ��]',
  email  varchar(50),
  LASTPASSWORDRESETDATE date,
  aexist INT DEFAULT 1      NOT NULL COMMENT '0Ϊͣ�� 1Ϊ����',
  login_time DATETIME                COMMENT '��¼ʱ��',
  doid   BIGINT COMMENT 'ҽ��id���' REFERENCES doctors (doid), -- ҽ����¼�ж��ĸ�ҽ������
  by1    VARCHAR(30) COMMENT '����',
  by2    BIGINT COMMENT '����2',
  CONSTRAINT pk_admins PRIMARY KEY (aid)
);

-- �û�(����Ա)-��ɫ
CREATE TABLE USER_AUTHORITY (
  USER_ID BIGINT, 
  AUTHORITY_ID BIGINT
);

-- ҩ����
CREATE TABLE prescripton (
  prid    BIGINT AUTO_INCREMENT COMMENT 'id',
  prdate  DATE COMMENT '��ҩʱ��',
  -- rid BIGINT references registration(rid)�� -- �Һŵ��в��˺�ҽ��id
  -- SQL ��ѯ�Ƚ��鷳 �����ϱ����� �Һŵ����ܿ�ҩ��סԺ
  cid     BIGINT COMMENT '���ƿ�id���' REFERENCES cards (cid),
  doid    BIGINT COMMENT 'ҽ��id' REFERENCES doctors (doid),
  dtotal  DOUBLE COMMENT '�ܼ�',
  drstate INT COMMENT '�Ƿ����',
  by1     VARCHAR(30) COMMENT '����',
  by2     BIGINT COMMENT '����2',
  CONSTRAINT pk_prescripton PRIMARY KEY (prid)
);

-- ҩƷ����
CREATE TABLE drugtype (
  dyid    BIGINT AUTO_INCREMENT COMMENT 'id',
  dyname  VARCHAR(30) NOT NULL  COMMENT 'ҩƷ����',
  dystate INT         NOT NULL  COMMENT '�Ƿ����',
  by1     VARCHAR(30) COMMENT '����',
  by2     BIGINT COMMENT '����2',
  CONSTRAINT pk_drugtype PRIMARY KEY (dyid)
);

-- ҩƷ��
CREATE TABLE drug (
  drid    BIGINT AUTO_INCREMENT COMMENT 'id',
  dyid    BIGINT COMMENT 'ҩƷ�������' REFERENCES drugtype (dyid),
  drname  VARCHAR(30) NOT NULL COMMENT 'ҩƷ����',
  drsum   INT         NOT NULL COMMENT '����',
  drprice DOUBLE      NOT NULL COMMENT '�۸�',
  drstate INT         NOT NULL COMMENT '�Ƿ����',
  by1     VARCHAR(30) COMMENT '����',
  by2     BIGINT COMMENT '����2',
  CONSTRAINT pk_drug PRIMARY KEY (drid)
);

-- ������
CREATE TABLE history (
  hiid   BIGINT AUTO_INCREMENT COMMENT 'id',
  cid    BIGINT COMMENT '���ƿ�id���' REFERENCES cards (cid),
  doid   BIGINT COMMENT 'ҽ��id���' REFERENCES doctors (doid),
  hidate DATE,
  -- rid BIGINT references registration(rid), -- �Һŵ���id���
  prid   BIGINT COMMENT 'ҩ�����' REFERENCES prescripton (prid),
  brief  VARCHAR(100) COMMENT '��Ͻ��',
  deal   INT NOT NULL COMMENT '1�ؼ� 2��ҩ 3סԺ',
  by1    VARCHAR(30)  COMMENT '����',
  by2    BIGINT       COMMENT '����2',
  CONSTRAINT pk_history PRIMARY KEY (hiid)
);

-- ҩƷ���ҹ�ϵ��
CREATE TABLE druganddeparts (
  drid BIGINT COMMENT 'ҩƷ������' REFERENCES drug (drid),
  deid BIGINT COMMENT '���ұ�����' REFERENCES departs (deid),
  by1  VARCHAR(30) COMMENT '����',
  by2  BIGINT COMMENT '����2',
  CONSTRAINT pk_druganddeparts PRIMARY KEY (drid, deid)
);

-- ҩƷ��ҩ����ϵ��
CREATE TABLE drugandprescripton (
  drid  BIGINT COMMENT 'ҩƷ������' REFERENCES drug (drid),
  prid  BIGINT COMMENT 'ҩ������' REFERENCES prescripton (prid),
  drnum INT NOT NULL COMMENT 'ҩƷ����',
  by1   VARCHAR(30)  COMMENT '����',
  by2   BIGINT       COMMENT '����2',
  CONSTRAINT pk_drugandprescripton PRIMARY KEY (drid, prid)
);

-- �������ͱ�
CREATE TABLE roomtype (
  rtid    BIGINT AUTO_INCREMENT COMMENT 'id',
  rtname  VARCHAR(30) NOT NULL  COMMENT '������',
  rtstate INT         NOT NULL  COMMENT '�Ƿ����',
  rtprice DOUBLE      NOT NULL  COMMENT '��λ�۸�',
  by1     VARCHAR(30) COMMENT '����',
  by2     BIGINT COMMENT '����2',
  CONSTRAINT pk_roomtype PRIMARY KEY (rtid)
);

-- ������
CREATE TABLE room (
  rmid    BIGINT AUTO_INCREMENT COMMENT 'id',
  rmname  VARCHAR(20) NOT NULL  COMMENT '��λ��',
  rtid    BIGINT COMMENT '��λ����id' REFERENCES roomtype (rtid),
  rmstate INT         NOT NULL  COMMENT '�Ƿ����',
  by1     VARCHAR(30) COMMENT '����',
  by2     BIGINT COMMENT '����2',
  CONSTRAINT pk_room PRIMARY KEY (rmid)
);

-- סԺ��Ϣ��
CREATE TABLE hospital (
  hoid      BIGINT AUTO_INCREMENT COMMENT 'id',
  rmid      BIGINT COMMENT '��λ��' REFERENCES romm (rmid),
  -- rid BIGINT references registration(rid)�� -- �Һŵ��в��˺�ҽ��id
  -- SQL ��ѯ�Ƚ��鷳 �����ϱ����� �Һŵ����ܿ�ҩ��סԺ
  pid       BIGINT COMMENT '����id' REFERENCES patients (pid),
  doid      BIGINT COMMENT 'ҽ��id' REFERENCES doctors (doid),
  startdate DATE COMMENT '��ҩʱ��',
  enddate   DATE COMMENT '��Ժʱ�� �����ý����ж��Ƿ��Ժ',
  conten    VARCHAR(100) COMMENT '������Ϣ',
  hostate   INT COMMENT '�Ƿ����',
  by1       VARCHAR(30) COMMENT '����',
  by2       BIGINT COMMENT '����2',
  CONSTRAINT pk_hospital PRIMARY KEY (hoid)
);



-- ��ʼ������ -------------------------------------------------------------
-- ��ɫ
insert into AUTHORITY(id,name,descr) values(0,'ROLE_ADMIN','��������Ա');
insert into AUTHORITY(id,name,descr) values(1,'ROLE_USER','��ͨ����Ա');
insert into AUTHORITY(id,name,descr) values(2,'ROLE_USER','ҽ��ͷ��');
insert into AUTHORITY(id,name,descr) values(3,'ROLE_USER','�Һ�����');
insert into AUTHORITY(id,name,descr) values(4,'ROLE_USER','���۷�ҩ');
commit;

-- ����
insert into departs(dename,intro,deexist)
values('�ڿ�','��ɳһ��',1);
insert into departs(dename,intro,deexist)
values('���','��ɳһ��',1);
insert into departs(dename,intro,deexist)
values('����','��ɳһ��',1);
insert into departs(dename,intro,deexist)
values('����','��ɳһ��',1);
insert into departs(dename,intro,deexist)
values('�ۿ�','��ɳһ��',1);
insert into departs(dename,intro,deexist)
values('���Ǻ��','��ɳһ��',1);
insert into departs(dename,intro,deexist)
values('��ǻ��','��ɳһ��',1);
insert into departs(dename,intro,deexist)
values('Ƥ����','��ɳһ��',1);
commit;

-- ҽ��
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('��һ��','����ҽʦ','��������',1,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monpm,tuepm,wedpm,thupm,fripm,pcreg,xcreg,doexist,bcost)
values('����','����ҽʦ','��������',2,1,1,1,1,1,100,100,1,100.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('�������','����ҽʦ','��������',3,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('����','����ҽʦ','��������',4,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('Ҷ��','������ҽʦ','��������',5,1,1,1,1,1,100,100,1,100.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('ʯ�','����ҽʦ','��������',6,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,satam,sunam,pcreg,xcreg,doexist,bcost)
values('����','����ҽʦ','��������',7,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,satpm,sumpm,pcreg,xcreg,doexist,bcost)
values('����','סԺҽʦ','��������',8,1,1,1,1,1,100,100,1,100.00);
commit;

-- ��������Ա
insert into admins(aname,pwd,state,aexist,email,LASTPASSWORDRESETDATE) values('admin','21232F297A5',0,1,'admin@gmail.com','2018-01-19');
-- ��ͨ����Ա
insert into admins(aname,pwd,state,aexist,email,LASTPASSWORDRESETDATE,by1) values('king','B2086154F10',1,1,'king@gmail.com','2018-01-19','king');
-- ҽ��
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('huyiqing','3B931C55FAB',2,1,1,'huyiqing@gmail.com','2018-01-19');
-- �Һ�����
insert into admins(aname,pwd,state,aexist,email,LASTPASSWORDRESETDATE,by1) values('smith','A66E44736E7',3,1,'smith@gmail.com','2018-01-19','smith');
-- ���۷�ҩ
insert into admins(aname,pwd,state,aexist,email,LASTPASSWORDRESETDATE,by1) values('scott','21F63C6E971',4,1,'scott@gmail.com','2018-01-19','scott');

-- ҽ��
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('xiaoyan','3A81B61E4BC',2,1,2,'xiaoyan@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('duguqiubai','F21A4518DDB',2,1,3,'duguqiubai@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('chennan','FE984889A9D',2,1,4,'chennan@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('yefan','9BCC0022ADA',2,1,5,'yefan@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('shihao','58FAF2E2D1A',2,1,6,'shihao@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('chufeng','61BB1C74925',2,1,7,'chufeng@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('qinyun','EB93175FF19',2,1,8,'qinyun@gmail.com','2018-01-19');
commit;

-- �û�(����Ա)-��ɫ
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 0);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 1); 
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (3, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (4, 3);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (5, 4);

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (6, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (7, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (8, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (9, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (10, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (11, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (12, 2);
commit;


-- ** Day 06 *************************************************
-- ���ƿ�
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('����','M','13807318888','430121198011288118','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('�Ƿ�','F','13807318887','430121198011288117','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('������','F','13807318886','430121198011288116','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('����','F','13807318885','430121198011288115','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('����','F','13807318884','430121198011288114','888888',50.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('������','M','13807318883','430121198011288113','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('������','M','13807318882','430121198011288112','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('����','M','13807318881','430121198011288111','888888',100.00,now());

insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('����','M','13807311111','430121199910010101','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('����','F','13807311112','430121199910010102','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('ŷ����','M','13807311113','430121199910010103','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('�','M','13807311114','430121199910010104','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('�����','F','13807311115','430121199910010105','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('���߹�','M','13807311116','430121199910010106','888888',100.00,now());
commit;

-- ��ԤԼ�Ű��
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(1,'2018-01-24 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(2,'2018-01-24 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(3,'2018-01-24 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(4,'2018-01-24 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(5,'2018-01-24 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(6,'2018-01-24 13:00:00',1,1,100,77,100,102);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(7,'2018-01-24 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(8,'2018-01-24 13:00:00',1,1,100,77,100,78);

insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(1,'2018-01-25 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(2,'2018-01-25 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(3,'2018-01-25 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(4,'2018-01-25 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(5,'2018-01-25 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(6,'2018-01-25 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(7,'2018-01-25 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(8,'2018-01-25 13:00:00',1,1,100,77,100,118);

insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(1,'2018-01-26 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(2,'2018-01-26 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(3,'2018-01-26 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(4,'2018-01-26 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(5,'2018-01-26 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(6,'2018-01-26 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(7,'2018-01-26 09:00:00',0,1,100,77,100,118);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(8,'2018-01-26 13:00:00',1,1,100,77,100,78);

insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(1,'2018-01-27 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(2,'2018-01-27 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(3,'2018-01-27 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(4,'2018-01-27 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(5,'2018-01-27 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(6,'2018-01-27 13:00:00',1,1,100,77,100,118);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(7,'2018-01-27 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(8,'2018-01-27 13:00:00',1,1,100,77,100,78);

insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(1,'2018-01-28 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(2,'2018-01-28 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(3,'2018-01-28 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(4,'2018-01-28 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(5,'2018-01-28 09:00:00',0,1,100,77,100,118);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(6,'2018-01-28 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(7,'2018-01-28 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(8,'2018-01-28 13:00:00',1,1,100,77,100,78);
commit;

-- �Һŵ��� (���ƿ�Ԥ���ĸ�ҽ���İ�)
insert into registration(cid,bid,snum,state)
values(1,1,1,1);
insert into registration(cid,bid,snum,state)
values(2,2,2,1);
insert into registration(cid,bid,snum,state)
values(3,3,3,1);
insert into registration(cid,bid,snum,state)
values(4,4,4,1);
insert into registration(cid,bid,snum,state)
values(5,5,5,1);
insert into registration(cid,bid,snum,state)
values(6,6,6,1);
insert into registration(cid,bid,snum,state)
values(7,7,7,1);
insert into registration(cid,bid,snum,state)
values(8,8,8,1);

insert into registration(cid,bid,snum,state)
values(9,9,9,1);
insert into registration(cid,bid,snum,state)
values(10,10,10,1);
insert into registration(cid,bid,snum,state)
values(11,11,11,1);
insert into registration(cid,bid,snum,state)
values(12,12,12,1);
insert into registration(cid,bid,snum,state)
values(13,13,13,1);
insert into registration(cid,bid,snum,state)
values(14,14,14,1);
insert into registration(cid,bid,snum,state)
values(1,15,15,1);
insert into registration(cid,bid,snum,state)
values(2,16,16,1);

insert into registration(cid,bid,snum,state)
values(3,17,17,1);
insert into registration(cid,bid,snum,state)
values(4,18,18,1);
insert into registration(cid,bid,snum,state)
values(5,19,19,1);
insert into registration(cid,bid,snum,state)
values(6,20,20,1);
insert into registration(cid,bid,snum,state)
values(7,21,21,1);
insert into registration(cid,bid,snum,state)
values(8,22,22,1);
insert into registration(cid,bid,snum,state)
values(9,23,23,1);
insert into registration(cid,bid,snum,state)
values(10,24,24,1);

insert into registration(cid,bid,snum,state)
values(11,25,25,1);
insert into registration(cid,bid,snum,state)
values(12,26,26,1);
insert into registration(cid,bid,snum,state)
values(13,27,27,1);
insert into registration(cid,bid,snum,state)
values(14,28,28,1);
insert into registration(cid,bid,snum,state)
values(1,29,28,1);
insert into registration(cid,bid,snum,state)
values(2,30,30,1);
insert into registration(cid,bid,snum,state)
values(3,31,31,1);
insert into registration(cid,bid,snum,state)
values(4,32,32,1);

insert into registration(cid,bid,snum,state)
values(5,33,33,1);
insert into registration(cid,bid,snum,state)
values(6,34,34,1);
commit;

-- �� bookable �е��ֶ� bdate �洢������ DATETIME | TIMESTAMP,��ѯ�������:
-- ��ѯ�������Ϣ:
select * from bookable where to_days(bdate) = to_days(now());

-- ��ѯ�������Ϣ:
select * from bookable where to_days(now()) - to_days(bdate) = 1;

-- ��ѯ�� 7 �����Ϣ:
select * from bookable where date_sub(curdate(), INTERVAL 7 DAY) <= date(bdate);

-- ��ѯ���ܵ���Ϣ:
select * from bookable where YEARWEEK(date_format(bdate,'%Y-%m-%d')) = YEARWEEK(now());

-- ��ѯ���ܵ���Ϣ:
select * from bookable where YEARWEEK(date_format(bdate,'%Y-%m-%d')) = YEARWEEK(now()) -1;

-- ��ѯ�� 30 �����Ϣ:
select * from bookable where date_sub(curdate(), INTERVAL 30 DAY) <= date(bdate);

-- ��ѯ���µ���Ϣ:
select * from bookable where date_format(bdate,'%Y%m') = date_format(curdate(),'%Y%m');

-- ��ѯ��һ�µ���Ϣ:
SELECT * FROM bookable WHERE PERIOD_DIFF(date_format(now( ),'%Y%m' ), date_format(bdate, '%Y%m' ))=1;

-- ��ѯ���뵱ǰ���� 6 ���µ���Ϣ:
select * from bookable where bdate between date_sub(now(),interval 6 month) and now();

-- ��ѯ�����ȵ���Ϣ:
select * from bookable where QUARTER(bdate)=QUARTER(now());

-- ��ѯ�ϼ��ȵ���Ϣ:
select * from bookable where QUARTER(bdate)=QUARTER(DATE_SUB(now(),interval 1 QUARTER));

-- ��ѯ�������Ϣ:
select * from bookable where YEAR(bdate) = YEAR(now());

-- ��ѯȥ�����Ϣ:
select * from bookable where YEAR(bdate) = YEAR(date_sub(now(),interval 1 year));

-- str_to_date('2018-01-17 17:20:30', '%Y-%m-%d %H:%i:%s')



-- ** Day 07 *************************************************

-- Spring Security use BCrypt
-- ��������Ա
update admins set pwd='$2a$10$dILTe4Ak8jVKdyxZGB1l9uu9CBYFFENkoEZ/aifBJNqxBKNd0JMES' where aid=1;                       
-- ��ͨ����Ա
update admins set pwd='$2a$10$j0JpxKHF/mk3W8gvvxncR..ks2UmHRTKVEw.ZO1AVZigDvCClNxkK' where aid=2;
-- ҽ��
update admins set pwd='$2a$10$aHL8.rdJgJXz/niWCb5jTOsgRiGvUb1tO8HVJAN1g2DxdkvWoCD0y' where aid=3;
-- �Һ�����
update admins set pwd='$2a$10$lI2TiMYD1F9mC4Gor7lspejf4ea2iF2kcy8OBjKLmbDY.rPEeM5la' where aid=4;
-- ���۷�ҩ
update admins set pwd='$2a$10$FpLWBp.Plo5G7e3TaMLgxul83HMnaMgQLNVR.mGHwBDbFi2IZCEmO' where aid=5;

-- ҽ��
update admins set pwd='$2a$10$C0j4kOwhnfEL5dpF2KbKlupMAfJxXlJbFsPy563z4TCoMhrJCsm8q' where aid=6;
update admins set pwd='$2a$10$oD9IMOakAzMmFBEpXPVuhO7b9rr7wb/AX7sgGz0xrvUDnNetfWuzK' where aid=7;
update admins set pwd='$2a$10$vmiW63Vvi6JGxYXgGfsQ0OjVuM3DqmkdXXJP/QGJPqk74PYr6FQGu' where aid=8;
update admins set pwd='$2a$10$qAZWUcPozR5sThtdb5I.N.wq09fscK.RVy28Ax1sfsfSBkndfHgse' where aid=9;
update admins set pwd='$2a$10$c6JAbfuvUHegciqkbgpa6.10bbI1E4Ze/VSnsXGRTF76Ajr54QA2W' where aid=10;
update admins set pwd='$2a$10$.BnCzJOcQVntuGDSl6mj3uIyJnaDHcC2kpuzFEIU6dCtGeJJhz6b2' where aid=11;
update admins set pwd='$2a$10$cY2f7xn6fge8eActkUgXM.8rZUIABUczPGO9egx1qpWPYSnRhesKK' where aid=12;
commit;



-- ** Day 10 *************************************************
-- ����ԤԼ���˱�
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('����','M','13807318888','430121198011288118','888888',1,'duany@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('�Ƿ�','F','13807318887','430121198011288117','888888',2,'qiaofeng@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('������','F','13807318886','430121198011288116','888888',3,'wangyuyan@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('����','F','13807318885','430121198011288115','888888',4,'azhu@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('����','F','13807318884','430121198011288114','888888',null,'azi@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('������','M','13807318883','430121198011288113','888888',6,'dingchunqiu@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('������','M','13807318882','430121198011288112','888888',7,'duanzhengchun@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('����','M','13807318881','430121198011288111','888888',8,'xuzhu@gmail.com');

insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('����','M','13807311111','430121199910010101','888888',9,'guojing@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('����','F','13807311112','430121199910010102','888888',null,'huangrong@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('ŷ����','M','13807311113','430121199910010103','888888',11,'ouyangke@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('�','M','13807311114','430121199910010104','888888',null,'yangkang@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('�����','F','13807311115','430121199910010105','888888',13,'nunianci@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('���߹�','M','13807311116','430121199910010106','888888',null,'hongqigong@gmail.com');
commit;


-- ����ԤԼ���� - (����ԤԼ�ĸ�ҽ���İ�) [��ǰ-�Һŵ���]
insert into reservation(pid,bid,state)
values(1,34,1);
insert into reservation(pid,bid,state)
values(2,33,1);
insert into reservation(pid,bid,state)
values(3,32,1);
insert into reservation(pid,bid,state)
values(4,31,1);
insert into reservation(pid,bid,state)
values(5,30,1);
insert into reservation(pid,bid,state)
values(6,29,1);
insert into reservation(pid,bid,state)
values(7,28,1);
insert into reservation(pid,bid,state)
values(8,27,1);

insert into reservation(pid,bid,state)
values(9,26,1);
insert into reservation(pid,bid,state)
values(10,25,1);
insert into reservation(pid,bid,state)
values(11,24,1);
insert into reservation(pid,bid,state)
values(12,23,1);
insert into reservation(pid,bid,state)
values(13,22,1);
insert into reservation(pid,bid,state)
values(14,21,1);
insert into reservation(pid,bid,state)
values(1,20,1);
insert into reservation(pid,bid,state)
values(2,19,1);

insert into reservation(pid,bid,state)
values(3,18,1);
insert into reservation(pid,bid,state)
values(4,17,1);
insert into reservation(pid,bid,state)
values(5,16,1);
insert into reservation(pid,bid,state)
values(6,15,1);
insert into reservation(pid,bid,state)
values(7,14,1);
insert into reservation(pid,bid,state)
values(8,13,1);
insert into reservation(pid,bid,state)
values(9,12,1);
insert into reservation(pid,bid,state)
values(10,11,1);

insert into reservation(pid,bid,state)
values(11,10,1);
insert into reservation(pid,bid,state)
values(12,9,1);
insert into reservation(pid,bid,state)
values(13,8,1);
insert into reservation(pid,bid,state)
values(14,7,1);
insert into reservation(pid,bid,state)
values(1,6,1);
insert into reservation(pid,bid,state)
values(2,5,1);
insert into reservation(pid,bid,state)
values(3,4,1);
insert into reservation(pid,bid,state)
values(4,3,1);

insert into reservation(pid,bid,state)
values(5,2,1);
insert into reservation(pid,bid,state)
values(6,1,1);
commit;



-- ��ʼ�� ȡԤԼ�� ���� -------------------------------------------------
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(1,'2018-01-30 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(2,'2018-01-30 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(3,'2018-01-30 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(4,'2018-01-30 13:00:00',1,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(5,'2018-01-30 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(6,'2018-01-30 13:00:00',1,1,100,77,100,102);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(7,'2018-01-30 09:00:00',0,1,100,77,100,78);
insert into bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum)
values(8,'2018-01-30 13:00:00',1,1,100,77,100,78);
commit;


-- ����ԤԼ���� - (����ԤԼ�ĸ�ҽ���İ�) [��ǰ-�Һŵ���]
insert into reservation(pid,bid,state)
values(1,104,1);
insert into reservation(pid,bid,state)
values(2,105,1);
insert into reservation(pid,bid,state)
values(3,106,1);
insert into reservation(pid,bid,state)
values(4,107,1);
insert into reservation(pid,bid,state)
values(5,108,1);
insert into reservation(pid,bid,state)
values(6,109,1);
insert into reservation(pid,bid,state)
values(7,110,1);
insert into reservation(pid,bid,state)
values(8,111,1);
commit;

-- huyiqing duguqiubai yefan chufeng

