-- Hospital for MySQL ------------------------------
-- by SONG 2017-12-28 ------------------------------

-- create database if not exists hospital;

-- 网上预约病人表
CREATE TABLE patients (
  pid     BIGINT AUTO_INCREMENT       COMMENT 'id',
  pname   VARCHAR(60)        NOT NULL COMMENT '姓名',
  gender  VARCHAR(1)         NOT NULL COMMENT '性别',
  phone   VARCHAR(11) UNIQUE NOT NULL COMMENT '手机号码', -- 根据手机号码或身份证号登录
  idcard  VARCHAR(20) UNIQUE NOT NULL COMMENT '身份证', -- 年龄可以根据身份证号计算
  pwd     VARCHAR(20)        NOT NULL COMMENT '密码',
  medcard BIGINT COMMENT '诊疗卡号',
  email   VARCHAR(40) COMMENT 'EMAIL',
  by1     VARCHAR(30) COMMENT '备用',
  by2     BIGINT COMMENT '备用2',
  CONSTRAINT pk_patients PRIMARY KEY (pid)
);

-- 诊疗卡
CREATE TABLE cards (
  cid       BIGINT AUTO_INCREMENT  COMMENT 'id',
  pname     VARCHAR(60)   NOT NULL COMMENT '姓名',
  gender    VARCHAR(1)    NOT NULL COMMENT '性别',
  phone     VARCHAR(20)   NOT NULL COMMENT '手机号码', -- 根据手机号码或身份证号登录
  idcard    VARCHAR(20)   NOT NULL COMMENT '身份证',
  pwd       VARCHAR(20)   NOT NULL COMMENT '密码',
  ramaining DOUBLE COMMENT '余额',
  newdate   DATE COMMENT '开卡时间',
  -- pid BIGINT references patients(pid),--病人的外键
  doexist   INT DEFAULT 1 NOT NULL COMMENT '0为停用 1为可用',
  by1       VARCHAR(30) COMMENT '备用',
  by2       BIGINT COMMENT '备用2',
  CONSTRAINT pk_cards PRIMARY KEY (cid)
);

-- 科室表
CREATE TABLE departs (
  deid    BIGINT AUTO_INCREMENT       COMMENT 'id',
  dename  VARCHAR(60) UNIQUE NOT NULL COMMENT '科室名',
  intro   VARCHAR(100) COMMENT '简介', 
  deexist INT DEFAULT 1      NOT NULL COMMENT '0为停用 1为可用',
  by1     VARCHAR(30) COMMENT '备用',
  by2     BIGINT COMMENT '备用2',
  CONSTRAINT pk_departs PRIMARY KEY (deid)
);

-- 医生表
CREATE TABLE doctors (
  doid    BIGINT AUTO_INCREMENT  COMMENT 'id',
  doname  VARCHAR(60)   NOT NULL COMMENT '医生名',
  title   VARCHAR(60)   NOT NULL COMMENT '医生职称',
  photo   VARCHAR(100) COMMENT '相片',
  info    VARCHAR(90) COMMENT '信息介绍',
  deid    BIGINT COMMENT '科室id外键' REFERENCES departs (deid),
  monam   INT COMMENT '周一上午是否值班,1为是',
  monpm   INT COMMENT '周一下午是否值班,1为是',
  tueam   INT COMMENT '周二上午',
  tuepm   INT COMMENT '周二下午',
  wedam   INT COMMENT '周三上午',
  wedpm   INT COMMENT '周三下午',
  thuam   INT COMMENT '周四上午',
  thupm   INT COMMENT '周四下午',
  friam   INT COMMENT '周五上午',
  fripm   INT COMMENT '周五下午',
  satam   INT COMMENT '周六上午',
  satpm   INT COMMENT '周六下午',
  sunam   INT COMMENT '周日上午',
  sumpm   INT COMMENT '周日下午',
  -- 每小时可预约人数(网上可预约多少人数,现场可预约多少人数)
  pcreg   INT COMMENT '网上可预约人数',
  xcreg   INT COMMENT '现场可预约人数',
  doexist INT DEFAULT 1 NOT NULL COMMENT '0为停用 1为可用',
  bcost   DOUBLE        NOT NULL COMMENT '该医生所需挂号费',
  by1     VARCHAR(30) COMMENT '备用',
  by2     BIGINT COMMENT '备用2',
  CONSTRAINT pk_doctors PRIMARY KEY (doid)
);

-- 可预约排班表
CREATE TABLE bookable (
  bid       BIGINT AUTO_INCREMENT COMMENT 'id',
  doid      BIGINT COMMENT '医生id外键' REFERENCES doctors (doid),
  bdate     DATETIME NOT NULL COMMENT '排班日期',
  starttime INT  NOT NULL COMMENT '时间段(0为上午 1为下午)', 
  used      INT  NOT NULL COMMENT '是否上线,0为否 1为是',
  bnum      INT  NOT NULL COMMENT '网上可预约数量',
  ynum      INT  NOT NULL COMMENT '网上已预约人数',
  xcum      INT  NOT NULL COMMENT '现场可预约数量',
  xcyum     INT  NOT NULL COMMENT '现场已预约数量',
  by1       VARCHAR(30) COMMENT '备用',
  by2       BIGINT COMMENT '备用2',
  CONSTRAINT pk_bookable PRIMARY KEY (bid)
);

-- 网上预约单表（以前-挂号单表）
CREATE TABLE reservation (
  red   BIGINT AUTO_INCREMENT COMMENT 'id',
  pid   BIGINT COMMENT '网上预约病人id外键' REFERENCES patients (pid),
  bid   BIGINT COMMENT '预约排班id外键' REFERENCES bookable (bid),
  state INT COMMENT '状态', -- 做退号时用
  by1   VARCHAR(30) COMMENT '备用',
  by2   BIGINT COMMENT '备用2',
  CONSTRAINT pk_reservation PRIMARY KEY (red)
);

-- 挂号单表
CREATE TABLE registration (
  rid   BIGINT AUTO_INCREMENT COMMENT 'id',
  cid   BIGINT COMMENT '诊疗卡id外键' REFERENCES cards (cid),
  bid   BIGINT COMMENT '预约排班id外键' REFERENCES bookable (bid),
  snum  INT COMMENT '票号',
  state INT COMMENT '状态(是否看完)',
  by1   VARCHAR(30) COMMENT '备用',
  by2   BIGINT COMMENT '备用2',
  CONSTRAINT pk_registration PRIMARY KEY (rid)
);

-- 取号表
CREATE TABLE takeble (
  tid  BIGINT AUTO_INCREMENT COMMENT 'id',
  rid  BIGINT COMMENT '挂号单表id外键' REFERENCES registration (rid),
  snum INT COMMENT '票号',
  by1  VARCHAR(30) COMMENT '备用',
  by2  BIGINT COMMENT '备用2',
  CONSTRAINT pk_takeble PRIMARY KEY (tid)
);

-- 角色表
CREATE TABLE AUTHORITY (
  id    BIGINT COMMENT 'id',
  name  VARCHAR(50),
  descr VARCHAR(50),
  by1    VARCHAR(30) COMMENT '备用',
  by2    BIGINT COMMENT '备用2',
  CONSTRAINT pk_role PRIMARY KEY (id)
);

-- 管理员表
CREATE TABLE admins (
  aid    BIGINT AUTO_INCREMENT       COMMENT 'id',
  aname  VARCHAR(50) UNIQUE NOT NULL COMMENT '登录用户名',
  pwd    VARCHAR(100)        NOT NULL COMMENT '密码',
  state  INT                NOT NULL COMMENT '角色等级 1为普通管理员 0为超级管理员 [对应角色表]',
  email  varchar(50),
  LASTPASSWORDRESETDATE date,
  aexist INT DEFAULT 1      NOT NULL COMMENT '0为停用 1为在用',
  login_time DATETIME                COMMENT '登录时间',
  doid   BIGINT COMMENT '医生id外键' REFERENCES doctors (doid), -- 医生登录判断哪个医生在用
  by1    VARCHAR(30) COMMENT '备用',
  by2    BIGINT COMMENT '备用2',
  CONSTRAINT pk_admins PRIMARY KEY (aid)
);

-- 用户(管理员)-角色
CREATE TABLE USER_AUTHORITY (
  USER_ID BIGINT, 
  AUTHORITY_ID BIGINT
);

-- 药方表
CREATE TABLE prescripton (
  prid    BIGINT AUTO_INCREMENT COMMENT 'id',
  prdate  DATE COMMENT '开药时间',
  -- rid BIGINT references registration(rid)， -- 挂号单有病人和医生id
  -- SQL 查询比较麻烦 流程上必须有 挂号单才能开药或住院
  cid     BIGINT COMMENT '诊疗卡id外键' REFERENCES cards (cid),
  doid    BIGINT COMMENT '医生id' REFERENCES doctors (doid),
  dtotal  DOUBLE COMMENT '总价',
  drstate INT COMMENT '是否结算',
  by1     VARCHAR(30) COMMENT '备用',
  by2     BIGINT COMMENT '备用2',
  CONSTRAINT pk_prescripton PRIMARY KEY (prid)
);

-- 药品类别表
CREATE TABLE drugtype (
  dyid    BIGINT AUTO_INCREMENT COMMENT 'id',
  dyname  VARCHAR(30) NOT NULL  COMMENT '药品类型',
  dystate INT         NOT NULL  COMMENT '是否可用',
  by1     VARCHAR(30) COMMENT '备用',
  by2     BIGINT COMMENT '备用2',
  CONSTRAINT pk_drugtype PRIMARY KEY (dyid)
);

-- 药品表
CREATE TABLE drug (
  drid    BIGINT AUTO_INCREMENT COMMENT 'id',
  dyid    BIGINT COMMENT '药品类型外键' REFERENCES drugtype (dyid),
  drname  VARCHAR(30) NOT NULL COMMENT '药品名称',
  drsum   INT         NOT NULL COMMENT '总数',
  drprice DOUBLE      NOT NULL COMMENT '价格',
  drstate INT         NOT NULL COMMENT '是否可用',
  by1     VARCHAR(30) COMMENT '备用',
  by2     BIGINT COMMENT '备用2',
  CONSTRAINT pk_drug PRIMARY KEY (drid)
);

-- 病例表
CREATE TABLE history (
  hiid   BIGINT AUTO_INCREMENT COMMENT 'id',
  cid    BIGINT COMMENT '诊疗卡id外键' REFERENCES cards (cid),
  doid   BIGINT COMMENT '医生id外键' REFERENCES doctors (doid),
  hidate DATE,
  -- rid BIGINT references registration(rid), -- 挂号单表id外键
  prid   BIGINT COMMENT '药方外键' REFERENCES prescripton (prid),
  brief  VARCHAR(100) COMMENT '诊断结果',
  deal   INT NOT NULL COMMENT '1回家 2开药 3住院',
  by1    VARCHAR(30)  COMMENT '备用',
  by2    BIGINT       COMMENT '备用2',
  CONSTRAINT pk_history PRIMARY KEY (hiid)
);

-- 药品科室关系表
CREATE TABLE druganddeparts (
  drid BIGINT COMMENT '药品表主键' REFERENCES drug (drid),
  deid BIGINT COMMENT '科室表主键' REFERENCES departs (deid),
  by1  VARCHAR(30) COMMENT '备用',
  by2  BIGINT COMMENT '备用2',
  CONSTRAINT pk_druganddeparts PRIMARY KEY (drid, deid)
);

-- 药品与药方关系表
CREATE TABLE drugandprescripton (
  drid  BIGINT COMMENT '药品表主键' REFERENCES drug (drid),
  prid  BIGINT COMMENT '药方主键' REFERENCES prescripton (prid),
  drnum INT NOT NULL COMMENT '药品数量',
  by1   VARCHAR(30)  COMMENT '备用',
  by2   BIGINT       COMMENT '备用2',
  CONSTRAINT pk_drugandprescripton PRIMARY KEY (drid, prid)
);

-- 病房类型表
CREATE TABLE roomtype (
  rtid    BIGINT AUTO_INCREMENT COMMENT 'id',
  rtname  VARCHAR(30) NOT NULL  COMMENT '类型名',
  rtstate INT         NOT NULL  COMMENT '是否可用',
  rtprice DOUBLE      NOT NULL  COMMENT '床位价格',
  by1     VARCHAR(30) COMMENT '备用',
  by2     BIGINT COMMENT '备用2',
  CONSTRAINT pk_roomtype PRIMARY KEY (rtid)
);

-- 病房表
CREATE TABLE room (
  rmid    BIGINT AUTO_INCREMENT COMMENT 'id',
  rmname  VARCHAR(20) NOT NULL  COMMENT '床位号',
  rtid    BIGINT COMMENT '床位类型id' REFERENCES roomtype (rtid),
  rmstate INT         NOT NULL  COMMENT '是否可用',
  by1     VARCHAR(30) COMMENT '备用',
  by2     BIGINT COMMENT '备用2',
  CONSTRAINT pk_room PRIMARY KEY (rmid)
);

-- 住院信息表
CREATE TABLE hospital (
  hoid      BIGINT AUTO_INCREMENT COMMENT 'id',
  rmid      BIGINT COMMENT '床位号' REFERENCES romm (rmid),
  -- rid BIGINT references registration(rid)， -- 挂号单有病人和医生id
  -- SQL 查询比较麻烦 流程上必须有 挂号单才能开药或住院
  pid       BIGINT COMMENT '病人id' REFERENCES patients (pid),
  doid      BIGINT COMMENT '医生id' REFERENCES doctors (doid),
  startdate DATE COMMENT '开药时间',
  enddate   DATE COMMENT '出院时间 ？另用结算判断是否出院',
  conten    VARCHAR(100) COMMENT '病人信息',
  hostate   INT COMMENT '是否结算',
  by1       VARCHAR(30) COMMENT '备用',
  by2       BIGINT COMMENT '备用2',
  CONSTRAINT pk_hospital PRIMARY KEY (hoid)
);



-- 初始化数据 -------------------------------------------------------------
-- 角色
insert into AUTHORITY(id,name,descr) values(0,'ROLE_ADMIN','超级管理员');
insert into AUTHORITY(id,name,descr) values(1,'ROLE_USER','普通管理员');
insert into AUTHORITY(id,name,descr) values(2,'ROLE_USER','医生头衔');
insert into AUTHORITY(id,name,descr) values(3,'ROLE_USER','挂号收银');
insert into AUTHORITY(id,name,descr) values(4,'ROLE_USER','划价发药');
commit;

-- 科室
insert into departs(dename,intro,deexist)
values('内科','长沙一绝',1);
insert into departs(dename,intro,deexist)
values('外科','长沙一绝',1);
insert into departs(dename,intro,deexist)
values('儿科','长沙一绝',1);
insert into departs(dename,intro,deexist)
values('妇科','长沙一绝',1);
insert into departs(dename,intro,deexist)
values('眼科','长沙一绝',1);
insert into departs(dename,intro,deexist)
values('耳鼻喉科','长沙一绝',1);
insert into departs(dename,intro,deexist)
values('口腔科','长沙一绝',1);
insert into departs(dename,intro,deexist)
values('皮肤科','长沙一绝',1);
commit;

-- 医生
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('胡一青','主治医师','手术厉害',1,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monpm,tuepm,wedpm,thupm,fripm,pcreg,xcreg,doexist,bcost)
values('萧炎','主任医师','手术厉害',2,1,1,1,1,1,100,100,1,100.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('独孤求败','主治医师','手术厉害',3,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('辰南','主治医师','手术厉害',4,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('叶凡','副主任医师','手术厉害',5,1,1,1,1,1,100,100,1,100.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,thuam,friam,pcreg,xcreg,doexist,bcost)
values('石昊','主治医师','手术厉害',6,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,satam,sunam,pcreg,xcreg,doexist,bcost)
values('楚风','主治医师','手术厉害',7,1,1,1,1,1,100,100,1,50.00);
insert into doctors(doname,title,info,deid,monam,tueam,wedam,satpm,sumpm,pcreg,xcreg,doexist,bcost)
values('秦云','住院医师','手术厉害',8,1,1,1,1,1,100,100,1,100.00);
commit;

-- 超级管理员
insert into admins(aname,pwd,state,aexist,email,LASTPASSWORDRESETDATE) values('admin','21232F297A5',0,1,'admin@gmail.com','2018-01-19');
-- 普通管理员
insert into admins(aname,pwd,state,aexist,email,LASTPASSWORDRESETDATE,by1) values('king','B2086154F10',1,1,'king@gmail.com','2018-01-19','king');
-- 医生
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('huyiqing','3B931C55FAB',2,1,1,'huyiqing@gmail.com','2018-01-19');
-- 挂号收银
insert into admins(aname,pwd,state,aexist,email,LASTPASSWORDRESETDATE,by1) values('smith','A66E44736E7',3,1,'smith@gmail.com','2018-01-19','smith');
-- 划价发药
insert into admins(aname,pwd,state,aexist,email,LASTPASSWORDRESETDATE,by1) values('scott','21F63C6E971',4,1,'scott@gmail.com','2018-01-19','scott');

-- 医生
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('xiaoyan','3A81B61E4BC',2,1,2,'xiaoyan@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('duguqiubai','F21A4518DDB',2,1,3,'duguqiubai@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('chennan','FE984889A9D',2,1,4,'chennan@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('yefan','9BCC0022ADA',2,1,5,'yefan@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('shihao','58FAF2E2D1A',2,1,6,'shihao@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('chufeng','61BB1C74925',2,1,7,'chufeng@gmail.com','2018-01-19');
insert into admins(aname,pwd,state,aexist,doid,email,LASTPASSWORDRESETDATE) values('qinyun','EB93175FF19',2,1,8,'qinyun@gmail.com','2018-01-19');
commit;

-- 用户(管理员)-角色
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
-- 诊疗卡
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('段誉','M','13807318888','430121198011288118','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('乔峰','F','13807318887','430121198011288117','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('王语嫣','F','13807318886','430121198011288116','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('阿朱','F','13807318885','430121198011288115','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('阿紫','F','13807318884','430121198011288114','888888',50.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('丁春秋','M','13807318883','430121198011288113','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('段正淳','M','13807318882','430121198011288112','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('虚竹','M','13807318881','430121198011288111','888888',100.00,now());

insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('郭靖','M','13807311111','430121199910010101','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('黄蓉','F','13807311112','430121199910010102','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('欧阳克','M','13807311113','430121199910010103','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('杨康','M','13807311114','430121199910010104','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('穆念慈','F','13807311115','430121199910010105','888888',100.00,now());
insert into cards(pname,gender,phone,idcard,pwd,ramaining,newdate)
values('洪七公','M','13807311116','430121199910010106','888888',100.00,now());
commit;

-- 可预约排班表
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

-- 挂号单表 (诊疗卡预订哪个医生的班)
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

-- 表 bookable 中的字段 bdate 存储类型是 DATETIME | TIMESTAMP,查询语句如下:
-- 查询今天的信息:
select * from bookable where to_days(bdate) = to_days(now());

-- 查询昨天的信息:
select * from bookable where to_days(now()) - to_days(bdate) = 1;

-- 查询近 7 天的信息:
select * from bookable where date_sub(curdate(), INTERVAL 7 DAY) <= date(bdate);

-- 查询本周的信息:
select * from bookable where YEARWEEK(date_format(bdate,'%Y-%m-%d')) = YEARWEEK(now());

-- 查询上周的信息:
select * from bookable where YEARWEEK(date_format(bdate,'%Y-%m-%d')) = YEARWEEK(now()) -1;

-- 查询近 30 天的信息:
select * from bookable where date_sub(curdate(), INTERVAL 30 DAY) <= date(bdate);

-- 查询本月的信息:
select * from bookable where date_format(bdate,'%Y%m') = date_format(curdate(),'%Y%m');

-- 查询上一月的信息:
SELECT * FROM bookable WHERE PERIOD_DIFF(date_format(now( ),'%Y%m' ), date_format(bdate, '%Y%m' ))=1;

-- 查询距离当前现在 6 个月的信息:
select * from bookable where bdate between date_sub(now(),interval 6 month) and now();

-- 查询本季度的信息:
select * from bookable where QUARTER(bdate)=QUARTER(now());

-- 查询上季度的信息:
select * from bookable where QUARTER(bdate)=QUARTER(DATE_SUB(now(),interval 1 QUARTER));

-- 查询本年的信息:
select * from bookable where YEAR(bdate) = YEAR(now());

-- 查询去年的信息:
select * from bookable where YEAR(bdate) = YEAR(date_sub(now(),interval 1 year));

-- str_to_date('2018-01-17 17:20:30', '%Y-%m-%d %H:%i:%s')



-- ** Day 07 *************************************************

-- Spring Security use BCrypt
-- 超级管理员
update admins set pwd='$2a$10$dILTe4Ak8jVKdyxZGB1l9uu9CBYFFENkoEZ/aifBJNqxBKNd0JMES' where aid=1;                       
-- 普通管理员
update admins set pwd='$2a$10$j0JpxKHF/mk3W8gvvxncR..ks2UmHRTKVEw.ZO1AVZigDvCClNxkK' where aid=2;
-- 医生
update admins set pwd='$2a$10$aHL8.rdJgJXz/niWCb5jTOsgRiGvUb1tO8HVJAN1g2DxdkvWoCD0y' where aid=3;
-- 挂号收银
update admins set pwd='$2a$10$lI2TiMYD1F9mC4Gor7lspejf4ea2iF2kcy8OBjKLmbDY.rPEeM5la' where aid=4;
-- 划价发药
update admins set pwd='$2a$10$FpLWBp.Plo5G7e3TaMLgxul83HMnaMgQLNVR.mGHwBDbFi2IZCEmO' where aid=5;

-- 医生
update admins set pwd='$2a$10$C0j4kOwhnfEL5dpF2KbKlupMAfJxXlJbFsPy563z4TCoMhrJCsm8q' where aid=6;
update admins set pwd='$2a$10$oD9IMOakAzMmFBEpXPVuhO7b9rr7wb/AX7sgGz0xrvUDnNetfWuzK' where aid=7;
update admins set pwd='$2a$10$vmiW63Vvi6JGxYXgGfsQ0OjVuM3DqmkdXXJP/QGJPqk74PYr6FQGu' where aid=8;
update admins set pwd='$2a$10$qAZWUcPozR5sThtdb5I.N.wq09fscK.RVy28Ax1sfsfSBkndfHgse' where aid=9;
update admins set pwd='$2a$10$c6JAbfuvUHegciqkbgpa6.10bbI1E4Ze/VSnsXGRTF76Ajr54QA2W' where aid=10;
update admins set pwd='$2a$10$.BnCzJOcQVntuGDSl6mj3uIyJnaDHcC2kpuzFEIU6dCtGeJJhz6b2' where aid=11;
update admins set pwd='$2a$10$cY2f7xn6fge8eActkUgXM.8rZUIABUczPGO9egx1qpWPYSnRhesKK' where aid=12;
commit;



-- ** Day 10 *************************************************
-- 网上预约病人表
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('段誉','M','13807318888','430121198011288118','888888',1,'duany@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('乔峰','F','13807318887','430121198011288117','888888',2,'qiaofeng@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('王语嫣','F','13807318886','430121198011288116','888888',3,'wangyuyan@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('阿朱','F','13807318885','430121198011288115','888888',4,'azhu@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('阿紫','F','13807318884','430121198011288114','888888',null,'azi@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('丁春秋','M','13807318883','430121198011288113','888888',6,'dingchunqiu@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('段正淳','M','13807318882','430121198011288112','888888',7,'duanzhengchun@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('虚竹','M','13807318881','430121198011288111','888888',8,'xuzhu@gmail.com');

insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('郭靖','M','13807311111','430121199910010101','888888',9,'guojing@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('黄蓉','F','13807311112','430121199910010102','888888',null,'huangrong@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('欧阳克','M','13807311113','430121199910010103','888888',11,'ouyangke@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('杨康','M','13807311114','430121199910010104','888888',null,'yangkang@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('穆念慈','F','13807311115','430121199910010105','888888',13,'nunianci@gmail.com');
insert into patients(pname,gender,phone,idcard,pwd,medcard,email)
values('洪七公','M','13807311116','430121199910010106','888888',null,'hongqigong@gmail.com');
commit;


-- 网上预约单表 - (网上预约哪个医生的班) [以前-挂号单表]
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



-- 初始化 取预约号 数据 -------------------------------------------------
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


-- 网上预约单表 - (网上预约哪个医生的班) [以前-挂号单表]
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

