
create database asmjava3
-- --------------------------------------------------------


-- Table structure for table `grade`


create table grade (
	Id int NOT NULL PRIMARY KEY IDENTITY(1,1) ,
	MaSV nvarchar(150) NOT NULL,
	HoTenSV nvarchar(150) NOT NULL,
	Photoshop float NOT NULL,
	Java float NOT NULL,
	Web float NOT NULL,
	DiemTB float NOT NULL

)

select * from grade

-- Data for table `grade`


INSERT INTO grade (MaSV, HoTenSV, Photoshop, Java, Web, DiemTB) VALUES
('Ps14754', N'Bùi Đức Minh', 9, 9, 10, 9.333333333333334),
('Ps14757', N'Trần Đức Hiệp', 10, 10, 10, 10),
('Ps14755', N'Đặng Phong Vũ', 8.5, 10, 10, 9.5),
('Ps14756', N'Long Văn An', 10, 10, 10, 10);

-- --------------------------------------------------------


-- Table structure for table `loginfrom`


CREATE TABLE loginform (
  id int DEFAULT NULL PRIMARY KEY ,
  username varchar(150) NOT NULL,
  password varchar(150) NOT NULL,
  role varchar(150) NOT NULL,
  email varchar(150) NOT NULL
 
) 
drop table loginform

-- Data for table `loginform`


INSERT INTO loginform VALUES
(1, 'minh', '123', 'qlsv','rongkho2@gmail.com'),
(2, 'huy', '123', 'qld','rongkho005@gmail.com');


select * from loginform

-- --------------------------------------------------------


-- Table structure for table `sinhvien`


CREATE TABLE sinhvien (

  MaSV nvarchar(150) NOT NULL PRIMARY KEY,
  HoTen nvarchar(150) NOT NULL,
  Email nvarchar(150) NOT NULL,
  SDT int NOT NULL,
  GioiTinh bit NOT NULL,
  DiaChi nvarchar(250) NOT NULL,
  HinhAnh varchar(250) NOT NULL
  
) 

select * from sinhvien

-- Data for table `sinhvien`



INSERT INTO sinhvien (MaSV, HoTen, Email, SDT, GioiTinh, DiaChi, HinhAnh) VALUES
('Ps14754', N'Bùi Đức Minh', 'minhbdps14733@fpt.edu.vn', 967329308, 1, N'Bảo Lộc', 'accept.png'),
('Ps14757', N'Trần Đức Hiệp', 'rongkho001@gmail.com', 917521408, 0, N'Đà Lạt', 'add.png'),
('Ps14755', N'Đặng Phong Vũ', 'rongkho002@gmail.com', 902361445, 1, N'Di Linh', 'background.jpg'),
('Ps14756', N'Long Văn An', 'rongkho004@gmail.com', 945631412, 1, N'Đức Trọng', 'business_user.png');




