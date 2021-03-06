USE [master]
GO
/****** Object:  Database [J3LP0003]    Script Date: 12/19/2020 11:05:56 PM ******/
CREATE DATABASE [J3LP0003]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'J3LP0003', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\J3LP0003.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'J3LP0003_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\J3LP0003_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [J3LP0003] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [J3LP0003].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [J3LP0003] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [J3LP0003] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [J3LP0003] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [J3LP0003] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [J3LP0003] SET ARITHABORT OFF 
GO
ALTER DATABASE [J3LP0003] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [J3LP0003] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [J3LP0003] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [J3LP0003] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [J3LP0003] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [J3LP0003] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [J3LP0003] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [J3LP0003] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [J3LP0003] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [J3LP0003] SET  DISABLE_BROKER 
GO
ALTER DATABASE [J3LP0003] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [J3LP0003] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [J3LP0003] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [J3LP0003] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [J3LP0003] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [J3LP0003] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [J3LP0003] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [J3LP0003] SET RECOVERY FULL 
GO
ALTER DATABASE [J3LP0003] SET  MULTI_USER 
GO
ALTER DATABASE [J3LP0003] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [J3LP0003] SET DB_CHAINING OFF 
GO
ALTER DATABASE [J3LP0003] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [J3LP0003] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [J3LP0003] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'J3LP0003', N'ON'
GO
USE [J3LP0003]
GO
/****** Object:  Table [dbo].[tblAccount]    Script Date: 12/19/2020 11:05:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblAccount](
	[email] [varchar](30) NOT NULL,
	[password] [varchar](20) NULL,
	[roleID] [varchar](10) NULL,
	[name] [nvarchar](30) NULL,
	[phone] [varchar](10) NULL,
	[address] [nvarchar](30) NULL,
	[createDate] [date] NULL,
	[status] [varchar](10) NULL,
 CONSTRAINT [PK_tblAccount] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblBooking]    Script Date: 12/19/2020 11:05:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblBooking](
	[bookingID] [varchar](50) NOT NULL,
	[userID] [varchar](30) NOT NULL,
	[bookingDate] [datetime] NULL,
	[totalPrice] [float] NULL,
	[checkInDate] [date] NULL,
	[checkOutDate] [date] NULL,
	[status] [varchar](10) NULL,
	[historyStatus] [varchar](10) NULL,
 CONSTRAINT [PK_tblBooking_1] PRIMARY KEY CLUSTERED 
(
	[bookingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblBookingDetail]    Script Date: 12/19/2020 11:05:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblBookingDetail](
	[bookingID] [varchar](50) NOT NULL,
	[roomID] [varchar](50) NOT NULL,
	[typeOfRoom] [nvarchar](20) NULL,
	[price] [float] NULL,
	[rating] [nchar](10) NULL,
 CONSTRAINT [PK_tblBookingDetail] PRIMARY KEY CLUSTERED 
(
	[bookingID] ASC,
	[roomID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRole]    Script Date: 12/19/2020 11:05:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRole](
	[roleID] [varchar](10) NOT NULL,
	[roleName] [varchar](20) NULL,
 CONSTRAINT [PK_tblRole] PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRoom]    Script Date: 12/19/2020 11:05:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRoom](
	[roomID] [varchar](50) NOT NULL,
	[typeOfRoomID] [varchar](20) NULL,
	[image] [varchar](20) NULL,
	[price] [float] NULL,
	[status] [varchar](10) NULL,
 CONSTRAINT [PK_tblRoom] PRIMARY KEY CLUSTERED 
(
	[roomID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblTypeOfRoom]    Script Date: 12/19/2020 11:05:56 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblTypeOfRoom](
	[typeOfRoomID] [varchar](20) NOT NULL,
	[typeOfRoomName] [nvarchar](30) NULL,
 CONSTRAINT [PK_tblTypeOfRoom] PRIMARY KEY CLUSTERED 
(
	[typeOfRoomID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblAccount] ([email], [password], [roleID], [name], [phone], [address], [createDate], [status]) VALUES (N'toluffy432a1@gmail.com', N'123456', N'US', N'Phúc', N'0903012268', N'91 Tung Thien Vuong', CAST(N'2020-12-19' AS Date), N'Active')
INSERT [dbo].[tblAccount] ([email], [password], [roleID], [name], [phone], [address], [createDate], [status]) VALUES (N'toluffy432a2@gmail.com', N'123456', N'US', N'Chop', N'0902012268', N'89 Tung Thien Vuong', CAST(N'2020-12-19' AS Date), N'Active')
INSERT [dbo].[tblBooking] ([bookingID], [userID], [bookingDate], [totalPrice], [checkInDate], [checkOutDate], [status], [historyStatus]) VALUES (N'a', N'toluffy432a1@gmail.com', CAST(N'2020-01-10 00:00:00.000' AS DateTime), 20, CAST(N'2020-01-11' AS Date), CAST(N'2020-01-12' AS Date), N'Active', N'Active')
INSERT [dbo].[tblBooking] ([bookingID], [userID], [bookingDate], [totalPrice], [checkInDate], [checkOutDate], [status], [historyStatus]) VALUES (N'toluffy432a1@gmail.com2020-12-19 22:57:41.477', N'toluffy432a1@gmail.com', CAST(N'2020-12-19 22:57:41.477' AS DateTime), 20, CAST(N'2021-01-19' AS Date), CAST(N'2021-01-20' AS Date), N'Active', N'Inactive')
INSERT [dbo].[tblBookingDetail] ([bookingID], [roomID], [typeOfRoom], [price], [rating]) VALUES (N'a', N'Room02', N'Vip', 20, NULL)
INSERT [dbo].[tblBookingDetail] ([bookingID], [roomID], [typeOfRoom], [price], [rating]) VALUES (N'toluffy432a1@gmail.com2020-12-19 22:57:41.477', N'Room02', N'Vip ', 20, NULL)
INSERT [dbo].[tblRole] ([roleID], [roleName]) VALUES (N'AD', N'Admin')
INSERT [dbo].[tblRole] ([roleID], [roleName]) VALUES (N'US', N'User')
INSERT [dbo].[tblRoom] ([roomID], [typeOfRoomID], [image], [price], [status]) VALUES (N'Room01', N'S', N'single.jpg', 50, N'Active')
INSERT [dbo].[tblRoom] ([roomID], [typeOfRoomID], [image], [price], [status]) VALUES (N'Room02', N'VIP', N'family.png', 20, N'Active')
INSERT [dbo].[tblRoom] ([roomID], [typeOfRoomID], [image], [price], [status]) VALUES (N'Room03', N'DB', N'double.jpg', 30, N'Active')
INSERT [dbo].[tblTypeOfRoom] ([typeOfRoomID], [typeOfRoomName]) VALUES (N'DB', N'Double')
INSERT [dbo].[tblTypeOfRoom] ([typeOfRoomID], [typeOfRoomName]) VALUES (N'F', N'Family')
INSERT [dbo].[tblTypeOfRoom] ([typeOfRoomID], [typeOfRoomName]) VALUES (N'S', N'Single')
INSERT [dbo].[tblTypeOfRoom] ([typeOfRoomID], [typeOfRoomName]) VALUES (N'VIP', N'Vip ')
ALTER TABLE [dbo].[tblAccount]  WITH CHECK ADD  CONSTRAINT [FK_tblAccount_tblRole] FOREIGN KEY([roleID])
REFERENCES [dbo].[tblRole] ([roleID])
GO
ALTER TABLE [dbo].[tblAccount] CHECK CONSTRAINT [FK_tblAccount_tblRole]
GO
ALTER TABLE [dbo].[tblBooking]  WITH CHECK ADD  CONSTRAINT [FK_tblBooking_tblAccount] FOREIGN KEY([userID])
REFERENCES [dbo].[tblAccount] ([email])
GO
ALTER TABLE [dbo].[tblBooking] CHECK CONSTRAINT [FK_tblBooking_tblAccount]
GO
ALTER TABLE [dbo].[tblBookingDetail]  WITH CHECK ADD  CONSTRAINT [FK_tblBookingDetail_tblBooking] FOREIGN KEY([bookingID])
REFERENCES [dbo].[tblBooking] ([bookingID])
GO
ALTER TABLE [dbo].[tblBookingDetail] CHECK CONSTRAINT [FK_tblBookingDetail_tblBooking]
GO
ALTER TABLE [dbo].[tblBookingDetail]  WITH CHECK ADD  CONSTRAINT [FK_tblBookingDetail_tblRoom] FOREIGN KEY([roomID])
REFERENCES [dbo].[tblRoom] ([roomID])
GO
ALTER TABLE [dbo].[tblBookingDetail] CHECK CONSTRAINT [FK_tblBookingDetail_tblRoom]
GO
ALTER TABLE [dbo].[tblRoom]  WITH CHECK ADD  CONSTRAINT [FK_tblRoom_tblTypeOfRoom] FOREIGN KEY([typeOfRoomID])
REFERENCES [dbo].[tblTypeOfRoom] ([typeOfRoomID])
GO
ALTER TABLE [dbo].[tblRoom] CHECK CONSTRAINT [FK_tblRoom_tblTypeOfRoom]
GO
USE [master]
GO
ALTER DATABASE [J3LP0003] SET  READ_WRITE 
GO
