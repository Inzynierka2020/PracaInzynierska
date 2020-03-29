CREATE DATABASE AviationModelling;
GO

USE [AviationModelling]
GO
/****** Object:  Table [dbo].[event]    Script Date: 29.03.2020 17:32:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[event](
	[event_id] [int] NOT NULL,
	[event_name] [varchar](100) NULL,
	[event_location] [varchar](100) NULL,
	[event_start_date] [varchar](100) NULL,
	[event_end_date] [varchar](100) NULL,
	[event_type] [varchar](100) NULL,
	[number_of_rounds] [int] NULL,
 CONSTRAINT [PK_event] PRIMARY KEY CLUSTERED 
(
	[event_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[flight]    Script Date: 29.03.2020 17:32:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[flight](
	[round_num] [int] NOT NULL,
	[pilot_id] [int] NOT NULL,
	[penalty] [int] NULL,
	[order_num] [int] NULL,
	[group_num] [varchar](100) NULL,
	[flight_time] [datetime] NULL,
	[wind_avg] [float] NULL,
	[dir_avg] [float] NULL,
	[seconds] [float] NULL,
	[sub1] [float] NULL,
	[sub2] [float] NULL,
	[sub3] [float] NULL,
	[sub4] [float] NULL,
	[sub5] [float] NULL,
	[sub6] [float] NULL,
	[sub7] [float] NULL,
	[sub8] [float] NULL,
	[sub9] [float] NULL,
	[sub10] [float] NULL,
	[sub11] [float] NULL,
	[dns] [bit] NULL,
	[dnf] [bit] NULL,
	[score] [float] NULL,
 CONSTRAINT [PK_round] PRIMARY KEY CLUSTERED 
(
	[round_num] ASC,
	[pilot_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pilot]    Script Date: 29.03.2020 17:32:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pilot](
	[pilot_id] [int] NOT NULL,
	[event_id] [int] NULL,
	[pilot_bib] [varchar](50) NULL,
	[first_name] [varchar](50) NULL,
	[last_name] [varchar](50) NULL,
	[pilot_class] [varchar](50) NULL,
	[ama] [varchar](50) NULL,
	[fai] [varchar](50) NULL,
	[fai_license] [varchar](50) NULL,
	[team_name] [varchar](50) NULL,
	[score] [float] NULL,
	[discarded1] [float] NULL,
	[discarded2] [float] NULL,
 CONSTRAINT [PK_Pilot] PRIMARY KEY CLUSTERED 
(
	[pilot_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[round]    Script Date: 29.03.2020 17:32:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[round](
	[round_num] [int] NOT NULL,
	[is_cancelled] [bit] NULL,
	[is_finished] [bit] NULL,
	[event_id] [int] NULL,
 CONSTRAINT [PK_round_1] PRIMARY KEY CLUSTERED 
(
	[round_num] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[pilot] ADD  DEFAULT ((0)) FOR [score]
GO
ALTER TABLE [dbo].[pilot] ADD  DEFAULT (NULL) FOR [discarded1]
GO
ALTER TABLE [dbo].[pilot] ADD  DEFAULT (NULL) FOR [discarded2]
GO
ALTER TABLE [dbo].[round] ADD  DEFAULT ((0)) FOR [is_cancelled]
GO
ALTER TABLE [dbo].[round] ADD  DEFAULT ((0)) FOR [is_finished]
GO
ALTER TABLE [dbo].[flight]  WITH CHECK ADD  CONSTRAINT [FK__flight__round_nu__51300E55] FOREIGN KEY([round_num])
REFERENCES [dbo].[round] ([round_num])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[flight] CHECK CONSTRAINT [FK__flight__round_nu__51300E55]
GO
ALTER TABLE [dbo].[flight]  WITH CHECK ADD  CONSTRAINT [FK__round__pilot_id__42E1EEFE] FOREIGN KEY([pilot_id])
REFERENCES [dbo].[pilot] ([pilot_id])
GO
ALTER TABLE [dbo].[flight] CHECK CONSTRAINT [FK__round__pilot_id__42E1EEFE]
GO
ALTER TABLE [dbo].[pilot]  WITH CHECK ADD  CONSTRAINT [FK__pilot__event_id__40058253] FOREIGN KEY([event_id])
REFERENCES [dbo].[event] ([event_id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[pilot] CHECK CONSTRAINT [FK__pilot__event_id__40058253]
GO
ALTER TABLE [dbo].[round]  WITH CHECK ADD  CONSTRAINT [round_event_event_id_fk] FOREIGN KEY([event_id])
REFERENCES [dbo].[event] ([event_id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[round] CHECK CONSTRAINT [round_event_event_id_fk]
GO
