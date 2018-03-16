# wireless-mrt-project
Repository for wireless mobile software engineering + pervasive computing final project

## Overview

Ubiquitous computing has become more and more a part of each and every human’s daily life. Computers are everywhere without people noticing it. The rise of smartphones has also contributed to the development of software and applications based on the mobile phone. People nowadays often use their mobile phone to assist them in daily activities, for example, finding transportations, ordering foods, paying bills, etc.

The increase in private transportation usage has led to the worsening of traffic in Jakarta. The government has advised the citizens to use the public transport rather than private transportation. One of the current public transport in Jakarta is Transjakarta. However, in recent years, the government has started the development of MRT (Mass Rapid Transport) to further reduce the congestion in Jakarta’s traffic.

In the last few years, we also saw the increase in demand for online transportation booking using mobile phone, for example, Go-Jek, Grab, and Uber. The existence of mobile applications helped increase the use of these services. However, for public transport in Indonesia, there is no existing solution to get more information before using the transportation/going to the station. We believe that if people are able to get more information, for example, the closest available station, ETA for train at the closest station, or which train to take if the current location is X and the destination is Y.

## Proposed Solution

A mobile application that can aid the user in finding transportation.

#### Application skeleton mockup

Live Map
![Live Map](https://github.com/christopher97/wireless-mrt-project/blob/master/images/Live%20Map.png)

Nearby
![Nearby](https://github.com/christopher97/wireless-mrt-project/blob/master/images/Nearby.png)

Travel
![Travel](https://github.com/christopher97/wireless-mrt-project/blob/master/images/Travel.png)

Station Detail
![Station Detail](https://github.com/christopher97/wireless-mrt-project/blob/master/images/Station%20Detail.png)

Sidebar
![Sidebar](https://github.com/christopher97/wireless-mrt-project/blob/master/images/SideBar.png)

Driver Login
![Driver Login](https://github.com/christopher97/wireless-mrt-project/blob/master/images/Driver%20Login.png)

Driver Train Data Input
![Train Data](https://github.com/christopher97/wireless-mrt-project/blob/master/images/Driver%20Train%20Data.png)

Driver Dashboard
![Dashboard](https://github.com/christopher97/wireless-mrt-project/blob/master/images/Driver%20Dashboard.png)

## Core Features:

+ Login/Logout for driver
+ Get the train ETA to the chosen station
+ Live map
+ Takes input of current location and destination, the app will yield the closest train station and the train station to stop at.
+ Using GPS, find the closest train station

## Tech Stack:

+ Java - Programming Language for Android
+ Android Studio - IDE for Android application development
+ Laravel - PHP framework for MRT API (might be replaced by xmysql)

## External Service

In order to make this application work there are some external services that the application will need to talk to:
+ Google Maps API
+ The MRT API

## Work Division

+ Christopher will work on API, UI design, accelerometer, directions, employee side
+ Dhyatmika will work on GPS, live map, nearby, encryption

## Milestones Timeline

Start: April 2nd, 2018

+ week 1 - creating layout for activities, work on API
+ week 2 - Design UI
+ week 3 - GPS function for location transmissions, Google Maps API
+ week 4 - Accelerometer function for backup movement detection
+ week 5 - Directions/travel feature, nearby feature
+ week 6 - Live map feature
+ week 7 - Employee/driver side development, design and login functionality
+ week 8 - Encryption for employee side
+ week 9 - report, finishing touches
