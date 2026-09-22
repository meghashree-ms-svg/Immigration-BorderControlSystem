
# Immigration Border Control System

A Java-based console application designed to manage immigration and visa processing workflows, including applicant registration, authentication, visa applications, document submission, embassy review, blacklist verification, and border entry control.

## 📌 Project Overview

The **Immigration Border Control System** is developed using **Java, MySQL, JDBC, and Cloudinary** to provide a structured system for managing immigration-related activities.

The system follows an object-oriented and modular architecture where different user roles have separate responsibilities. Applicants can register, log in, apply for visas, view visa status, and submit required documents. Embassy officers can review visa applications and approve or reject them. Border control officers can verify travellers and check blacklist records before allowing or denying entry.

The project also integrates **Cloudinary** for cloud-based document uploading and **MySQL** for persistent data storage.

## 🎯 Objectives

- Manage applicant registration and authentication.
- Allow applicants to submit visa applications.
- Track and update visa application status.
- Manage embassy officer operations.
- Upload and manage visa-related documents.
- Verify applicants before border entry.
- Maintain blacklist records for restricted applicants.
- Automatically deny entry for applicants with an active blacklist record.
- Store and manage immigration data using MySQL.
- Apply Object-Oriented Programming principles for modular development.

## ✨ Features

### 👤 Applicant Management

- Applicant registration
- Applicant login
- Input validation
- Visa application submission
- View visa application status
- Complete missing document requirements

### 🛂 Visa Management

- Create visa applications
- Store visa information in MySQL
- View visa details
- Track application status
- Embassy officer approval/rejection
- Applicant-specific visa status checking

### 🏛️ Embassy Officer Management

- Officer login
- View visa applications
- Review applicant applications
- Approve or reject visa applications
- Update visa status

### 📄 Document Management

- Submit required visa documents
- Track document verification status
- Identify mandatory and pending documents
- Upload documents using Cloudinary
- Store document information in the database

### 🚨 Blacklist Management

- Maintain blacklist records
- Store blacklist reason and status
- Check applicants during border verification
- Automatically deny entry for applicants with an active blacklist record

### 🛃 Border Control

- Verify traveller information
- Check visa status
- Perform blacklist verification
- Allow or deny border entry based on verification results
- Maintain border entry/exit information

## 🏗️ System Architecture

The project follows a modular layered structure:

```text
Immigration-BorderControlSystem
│
├── src
│   ├── model
│   │   ├── User.java
│   │   ├── Applicant.java
│   │   ├── Visa.java
│   │   ├── VisaDocument.java
│   │   ├── EmbassyOfficer.java
│   │   └── BorderControlOfficer.java
│   │
│   ├── repository
│   │   ├── ApplicantRepository.java
│   │   ├── VisaRepository.java
│   │   ├── VisaDocumentRepository.java
│   │   └── EmbassyOfficerRepository.java
│   │
│   ├── service
│   │   ├── ApplicantService.java
│   │   ├── VisaService.java
│   │   ├── LoginService.java
│   │   └── OfficerLoginService.java
│   │
│   ├── util
│   │   ├── DatabaseConnection.java
│   │   ├── Validation.java
│   │   ├── InputHelper.java
│   │   └── CloudinaryUploader.java
│   │
│   ├── menu
│   │   ├── MainMenu.java
│   │   ├── ApplicantMenu.java
│   │   └── OfficerMenu.java
│   │
│   └── Main.java
│
└── README.md
````

## 🔄 System Workflow

```text
Applicant Registration
        ↓
Applicant Login
        ↓
Visa Application
        ↓
Document Submission
        ↓
Embassy Officer Review
        ↓
Visa Approval / Rejection
        ↓
Border Verification
        ↓
Blacklist Check
        ↓
Entry Allowed / Entry Denied
```

## 🧩 Technologies Used

 Technology                   Purpose                          
 
 Java 21                      Core application development     
 Object-Oriented Programming  Modular system design            
 JDBC                         Java–MySQL database connectivity 
 MySQL                        Persistent data storage          
 IntelliJ IDEA                Development environment          
 Cloudinary                   Cloud-based document uploading   
 Git                          Version control                  
 GitHub                       Source code management           

## 🗄️ Database

The project uses a MySQL database named:

```text
immigration_system
```

Major database tables include:

* `applicants`
* `visas`
* `embassy_officers`
* `border_control_officers`
* `blacklist`
* `visa_documents`

The application uses **JDBC** and SQL queries to perform database operations such as:

* INSERT
* SELECT
* UPDATE
* Authentication queries
* Visa status updates
* Blacklist verification
* Document management

## 🔐 Security and Validation

The system includes validation mechanisms for:

* Applicant registration
* Login credentials
* Required input fields
* Visa application details
* Document information
* Officer authentication
* Blacklist verification

During border verification, an applicant with an **ACTIVE** blacklist record is denied entry.

## ☁️ Cloudinary Integration

Cloudinary is integrated into the project for uploading visa-related documents.

The document workflow is:

```text
Applicant
    ↓
Select Document
    ↓
Cloudinary Upload
    ↓
Upload Response
    ↓
Document Information Stored
    ↓
Verification Process
```

## 🧱 Object-Oriented Design

The project applies major Object-Oriented Programming concepts.

### Encapsulation

Classes use private fields with appropriate getters and setters to control access to data.

### Inheritance

Role-specific classes extend the common `User` class.

```text
              User
                |
       ---------------------
       |         |         |
   Applicant  Embassy   Border Control
              Officer      Officer
```

### Abstraction

Application responsibilities are separated into models, repositories, services, utilities, and menus.

### Polymorphism

Different user roles provide role-specific behaviour and menu operations while sharing common user functionality.

## 📂 Project Structure

### Model

Contains the entities used by the application.

Examples:

* `User`
* `Applicant`
* `Visa`
* `VisaDocument`
* `EmbassyOfficer`
* `BorderControlOfficer`

### Repository

Handles database operations using JDBC.

### Service

Contains application and business logic.

### Utility

Provides common functionality such as:

* Database connection
* Input handling
* Validation
* Cloudinary uploading

### Menu

Handles the console-based user interface and role-specific navigation.

## 🧪 Testing

The application was tested using different functional workflows including:

* Applicant registration
* Applicant login
* Visa application
* Visa status checking
* Embassy officer login
* Visa approval/rejection
* Document submission
* Database insertion and retrieval
* Input validation
* Blacklist verification
* Border entry decision

### Example Blacklist Test

An applicant with an active blacklist record was tested during border verification.

```text
Applicant ID      : APP18617
Passport Number   : p1234567
Reason            : Forged Documents
Status            : ACTIVE
```

During border verification, the system detected the active blacklist record and denied entry.

```text
BLACKLIST CHECK
        ↓
ACTIVE RECORD FOUND
        ↓
ENTRY DENIED
```

## 📊 Key Outcomes

The completed system provides:

* Role-based immigration workflow
* Database-backed visa management
* Applicant authentication
* Embassy application processing
* Document management
* Cloud-based document uploading
* Automated blacklist verification
* Border entry decision handling
* Modular Java architecture
* Persistent MySQL data storage

## 🚀 Future Enhancements

The following features can be added in future versions:

* Web-based user interface
* Mobile application support
* Password hashing and stronger authentication
* Role-based access control
* OCR-based document verification
* AI-assisted document validation
* Advanced immigration reports and analytics
* Cloud deployment
* Automated email/SMS notifications
* Integration with external immigration databases
* Scalable REST API architecture

## 👩‍💻 Team

**Project:** Immigration Border Control System

**Team Members:**

* Megha Shree R
* Nithiyasri R

**Institution:** Chennai Institute of Technology

**Department:** Computer Science and Engineering

## 📌 Project Type

**Academic / PBL Project**

This project was developed as part of the Problem-Based Learning (PBL) coursework to demonstrate practical application of Java programming, Object-Oriented Programming, database management, JDBC, software architecture, and system integration.

## 📜 License

This project is developed for academic and educational purposes.

```
```
# Immigration-BorderControlSystem
