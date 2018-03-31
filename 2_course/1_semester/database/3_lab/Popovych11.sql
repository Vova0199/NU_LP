/*    ==Scripting Parameters==

    Source Server Version : SQL Server 2016 (13.0.4206)
    Source Database Engine Edition : Microsoft SQL Server Express Edition
    Source Database Engine Type : Standalone SQL Server

    Target Server Version : SQL Server 2016
    Target Database Engine Edition : Microsoft SQL Server Express Edition
    Target Database Engine Type : Standalone SQL Server
*/

USE [Popovych11]
GO

/****** Object:  Table [dbo].[laba_3]    Script Date: 09.11.2017 17:58:21 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[laba_3](
	[id] [int] NOT NULL,
	[type] [nchar](10) NULL,
	[title] [nchar](15) NOT NULL,
	[conternt] [text] NULL,
	[date_of_receiving] [smalldatetime] NULL,
	[date_of_creation] [smalldatetime] NULL,
	[duration] [int] NULL,
	[author] [nchar](10) NOT NULL,
	[photo_author] [image] NULL,
	[authors_adress] [nchar](20) NULL,
	[agency_author] [nchar](10) NULL,
	[adress_agency] [nchar](20) NULL,
	[NSM_director] [nchar](30) NULL,
	[phone_agency] [nchar](10) NULL,
	[number_cassette] [nchar](10) NULL,
 CONSTRAINT [PK_laba_3] PRIMARY KEY CLUSTERED 
(
	[id] ASC,
	[title] ASC,
	[author] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [IX_laba_3] UNIQUE NONCLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[laba_3]  WITH CHECK ADD  CONSTRAINT [CK_laba_3] CHECK  (([date_of_receiving]>[date_of_creation]))
GO

ALTER TABLE [dbo].[laba_3] CHECK CONSTRAINT [CK_laba_3]
GO

ALTER TABLE [dbo].[laba_3]  WITH CHECK ADD  CONSTRAINT [CK_laba_3_1] CHECK  (([duration]>=(1)))
GO

ALTER TABLE [dbo].[laba_3] CHECK CONSTRAINT [CK_laba_3_1]
GO


