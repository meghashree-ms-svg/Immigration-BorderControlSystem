
# Immigration Border Control System

A Java-based console application designed to manage immigration and visa processing workflows, including applicant registration, authentication, visa applications, document submission, embassy review, blacklist verification, and traveller entry and exit management.

## 📌 Project Overview

The **Immigration Border Control System** is developed using **Java, MySQL, JDBC, and Cloudinary** to provide a structured system for managing immigration-related activities.

The system follows an object-oriented and modular architecture with separate roles for applicants, embassy officers, and border control officers. Applicants can register, log in, apply for visas, check visa status, complete required documents, and upload documents. Embassy officers can review visa applications and approve or reject them. Border control officers can verify travellers, check visa approval and blacklist status, and record traveller entry and exit movements.

The project uses **MySQL** for persistent data storage and **Cloudinary** for cloud-based document uploading.

## 🎯 Objectives

- Manage applicant registration and authentication.
- Provide secure role-based access for different users.
- Allow applicants to submit visa applications.
- Check visa eligibility before application.
- Manage required visa documents.
- Upload visa documents using Cloudinary.
- Track visa application and document status.
- Allow embassy officers to review visa applications.
- Approve or reject visa applications.
- Verify travellers during border entry and exit.
- Check active blacklist records.
- Record traveller entry and exit movements.
- Prevent entry when visa requirements or blacklist conditions are not satisfied.
- Store immigration data using MySQL.
- Apply Object-Oriented Programming principles and layered architecture.

## ✨ Features

### 👤 Applicant Management

- Applicant registration
- Applicant login
- Login retry option
- Forgot password functionality
- Input validation
- Applicant-specific visa management
- Visa application submission
- Visa status checking
- Visa eligibility checking
- Complete missing document requirements

### 🛂 Visa Management

- Create visa applications
- Support for different visa types:
  - Student
  - Tourist
  - Business
  - Work
  - General
- Store visa information in MySQL
- View visa details
- Track visa application status
- Prevent duplicate visa applications
- Embassy officer approval or rejection
- Applicant-specific visa status checking

### 📄 Visa Document Management

- Automatic document requirement checklist
- Mandatory document tracking
- Document submission
- Missing document completion
- Document verification status
- Pending and rejected document tracking
- Document count management
- Cloudinary-based document uploading

### ☁️ Cloudinary Integration

Visa-related documents can be uploaded to Cloudinary through the application.

The document upload process is:

```text
Applicant
    ↓
Select Required Document
    ↓
Upload Document
    ↓
Cloudinary
    ↓
Upload Response
    ↓
Document Information
    ↓
Visa Document Record
````

### 🏛️ Embassy Officer Management

* Embassy officer login
* View visa applications
* Review visa applications
* View applicant and visa details
* Review submitted documents
* Approve visa applications
* Reject visa applications
* Update visa status

### 🛃 Border Control Management

* Border control officer login
* Traveller verification
* Applicant identification
* Visa approval verification
* Blacklist verification
* Border entry processing
* Border exit processing
* Entry and exit record management
* Prevention of invalid or unauthorized entry
* Display traveller movement records

### 🚨 Blacklist Management

* Maintain blacklist records
* Store applicant and passport information
* Store blacklist reason
* Track blacklist status
* Check active blacklist records during border verification
* Deny entry when an active blacklist record is found

### 🚶 Traveller Movement Management

The system maintains traveller movement information through dedicated repository components.

It supports:

* Recording traveller entry
* Recording traveller exit
* Checking active entry status
* Preventing invalid duplicate entry records
* Displaying traveller movement records

## 🏗️ System Architecture

The project follows a layered and modular architecture:

```text
User
  │
  ├── Applicant
  │
  ├── Embassy Officer
  │
  └── Border Control Officer
          │
          ↓
      Menu Layer
          │
          ↓
      Service Layer
          │
          ↓
    Repository Layer
          │
          ↓
      MySQL Database

Applicant Documents
          │
          ↓
      Cloudinary
```

## 📂 Project Structure

```text
Immigration-BorderControlSystem
│
├── src
│   │
│   ├── menu
│   │   ├── ApplicantMenu.java
│   │   ├── MainMenu.java
│   │   └── OfficerMenu.java
│   │
│   ├── model
│   │   ├── Applicant.java
│   │   ├── BorderControlOfficer.java
│   │   ├── EmbassyOfficer.java
│   │   ├── User.java
│   │   ├── Visa.java
│   │   └── VisaDocument.java
│   │
│   ├── repository
│   │   ├── ApplicantRepository.java
│   │   ├── BorderControlOfficerRepository.java
│   │   ├── BorderTravelRepository.java
│   │   ├── EmbassyOfficerRepository.java
│   │   ├── TravellerMovementRepository.java
│   │   ├── VisaDocumentRepository.java
│   │   └── VisaRepository.java
│   │
│   ├── service
│   │   ├── ApplicantService.java
│   │   ├── BorderControlLoginService.java
│   │   ├── BorderControlService.java
│   │   ├── LoginService.java
│   │   ├── OfficerLoginService.java
│   │   ├── VisaDocumentService.java
│   │   └── VisaService.java
│   │
│   ├── util
│   │   ├── CloudinaryUploader.java
│   │   ├── DatabaseConnection.java
│   │   ├── InputHelper.java
│   │   └── Validation.java
│   │
│   └── Main.java
│
└── README.md
```

## 🔄 System Workflow

```text
Applicant Registration
        ↓
Applicant Login
        ↓
Visa Eligibility Check
        ↓
Visa Application
        ↓
Document Requirement Checklist
        ↓
Document Upload
        ↓
Embassy Officer Review
        ↓
Visa Approval / Rejection
        ↓
Border Control Verification
        ↓
Visa Approval Check
        ↓
Blacklist Check
        ↓
Entry Allowed / Entry Denied
        ↓
Traveller Entry / Exit Record
```

## 🧩 Technologies Used

| Technology                  | Purpose                           |
| --------------------------- | --------------------------------- |
| Java 21                     | Core application development      |
| Object-Oriented Programming | Application design and modularity |
| JDBC                        | Java-MySQL database connectivity  |
| MySQL                       | Persistent data storage           |
| IntelliJ IDEA               | Development environment           |
| Cloudinary                  | Cloud-based document uploading    |
| Git                         | Version control                   |
| GitHub                      | Source code management            |

## 🗄️ Database

The project uses a MySQL database named:

```text
immigration_system
```

The system manages data related to:

* Applicants
* Visas
* Embassy officers
* Border control officers
* Visa documents
* Blacklisted applicants
* Traveller movements

The application uses JDBC to perform database operations including:

* INSERT
* SELECT
* UPDATE
* Authentication
* Visa status updates
* Document management
* Traveller entry records
* Traveller exit records
* Blacklist verification

## 🔐 Validation

The system provides validation for important user inputs, including:

* Name
* Username
* Password
* Passport number
* Age
* Nationality
* Visa eligibility

### Password Validation

Passwords are checked for:

* Minimum length
* Uppercase letter
* Lowercase letter
* Number
* Special character
* No spaces

### Passport Validation

Passport numbers follow the required format:

```text
P1234567
```

### Visa Eligibility

The system checks conditions such as:

* Applicant details availability
* Age requirements for Work and Business visas
* Existing pending or approved visa applications
* Passport validity

## 🧱 Object-Oriented Design

The project applies major Object-Oriented Programming concepts.

### Encapsulation

Class attributes are maintained using private fields with getters and setters.

### Inheritance

Role-specific classes extend the common `User` class.

```text
                 User
                   │
        ┌──────────┼──────────┐
        ↓          ↓          ↓
   Applicant   Embassy    Border Control
               Officer       Officer
```

### Abstraction

The application separates responsibilities into:

* Model
* Repository
* Service
* Utility
* Menu

### Polymorphism

Different user roles provide their own role-specific menu behaviour while sharing common functionality from the parent `User` class.

## 🧪 Testing

The system supports testing of major workflows including:

* Applicant registration
* Applicant authentication
* Password reset
* Visa eligibility validation
* Visa application
* Duplicate visa application prevention
* Document checklist
* Document upload
* Embassy officer authentication
* Visa approval
* Visa rejection
* Border control authentication
* Visa approval verification
* Blacklist verification
* Traveller entry
* Traveller exit
* Traveller movement records
* Database operations

### Example Border Verification

During border verification, the system checks:

```text
Traveller
    ↓
Applicant Verification
    ↓
Visa Approval Check
    ↓
Blacklist Check
    ↓
Entry Decision
```

If an applicant has an active blacklist record, the system denies entry.

## 📊 Key Outcomes

The completed system provides:

* Role-based immigration processing
* Applicant registration and authentication
* Visa eligibility checking
* Visa application management
* Document requirement management
* Cloudinary document uploading
* Embassy visa review
* Visa approval and rejection
* Border control verification
* Blacklist checking
* Traveller entry and exit tracking
* MySQL-based persistent storage
* Modular Java architecture

## 🚀 Future Enhancements

Possible future enhancements include:

* Web-based user interface
* Mobile application
* Password hashing and stronger authentication
* Role-based access control
* OCR-based document verification
* AI-assisted document verification
* Advanced reporting and analytics
* Email and SMS notifications
* Cloud deployment
* REST API integration
* Integration with external immigration systems

## 👩‍💻 Team

**Project:** Immigration Border Control System

**Team Members:**

* Megha Shree R
* Nithiyasri R

**Institution:** Chennai Institute of Technology

**Department:** Computer Science and Engineering

## 📌 Project Type

**Academic / PBL Project**

This project was developed as part of the Problem-Based Learning (PBL) coursework to demonstrate practical application of Java programming, Object-Oriented Programming, database management, JDBC, cloud document uploading, authentication, and immigration workflow management.



