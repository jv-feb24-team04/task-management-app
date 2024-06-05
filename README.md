# TASK MANAGEMENT SYSTEM

## 👋 Introduction 👋

This is a simple and convenient app for managing tasks through projects. It lets users create and follow up on task progress with integrated notifications via Telegram messenger.

## 👩‍💻Technology Stack 👩‍💻
- Programming Language: Java
- Application Configuration: Spring Boot, Spring, Maven
- Accessing Data Spring Data: JPA, Hibernate, MySQL, PostgreSQL
- Web Development: Spring MVC, Servlets, JSP, Tomcat
- Testing and Documentation: JUnit, Mockito, Swagger, TestContainers
- Version Control: Git
- Infrastructure: Docker
- Database migration: Liquibase


## 🚀 Functionality 🚀
**click the description to try out the request in Postman**
### 1. **Authentication Controller**:
- `POST api/auth/registration` - [register a new user](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-4e280d43-6050-424c-adb9-893af6571985?action=share&creator=31607463&ctx=documentation)
- `POST api/auth/login` - [user login](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-317053c5-d9ae-48e8-8564-66d9690301cc?action=share&creator=31607463&ctx=documentation)

### 2. **User Controller**:
- `GET api/users/me` - [get user info](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-20d0bf55-310a-47c5-a97b-b0251ea41013?action=share&creator=31607463&ctx=documentation)
- `PUT api/users/me` - [update user info](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-ba1f5558-c171-4112-b1aa-1af63dc396a2?action=share&creator=31607463&ctx=documentation)
- `PUT api/users/{id}/role` - [update user's role](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-20d0bf55-310a-47c5-a97b-b0251ea41013?action=share&creator=31607463&ctx=documentation)

### 3. **Project Controller**:
- `POST api/projects` -[create a new project](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-1de87c65-065c-4f8c-b6ca-24a000dff706?action=share&creator=31607463&ctx=documentation)
- `GET api/projects` - [get all projects details](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-7ea9c108-3621-4d49-b34e-9dcf7ededa02?action=share&creator=31607463&ctx=documentation)
- `GET api/projects/{id}` - [get project details by project ID](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-6b8195be-b4a8-49ed-8782-43576d6f6d0b?action=share&creator=31607463&ctx=documentation)
- `PUT api/projects/{id}` - [update project](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-a2fa5b18-cb47-4140-8b47-64303af5c3b3?action=share&creator=31607463&ctx=documentation)
- `PATCH api/projects/{id}` - [update project status](https://www.postman.com/descent-module-candidate-79416993/workspace/task-management-system/request/31607463-7708d9dd-a14f-4bf0-9010-1965803a5fa6?action=share&creator=31607463&ctx=documentation)

### 4. **Task Controller**:
- `POST api/tasks` - create a new task for a project
- `GET api/tasks`- get all tasks details by project ID
- `GET api/tasks{id}` - get task details by task ID
- `PUT api/tasks/{id}` - update task details by task ID
- `DELETE api/tasks/{id}` - delete task by ID

### 5. **Label Controller**
- `POST api/labels` - [create new label]
- `GET api/labels/by_project/{projectId}` - [get all labels within the project]
- `PUT api/labels/{id}` - update label
- `DELETE api/labels/{id}` - delete label by id

### 6. **Comment Controller**:
- `POST api/comments/{taskId}` - add a new comment to a task by task ID
- `GET api/comments/{taskId}` - retrieve comments within a task
- `PUT api/comments/{id}` - update a comment
- `DELETE api/comments/{id} - delete a comment by ID

### 7. **Attachment Controller**:
- `POST api/attachments`
- `GET api/attachments/{id}` - get attachment details by ID
- `DELETE api/attachments/{id}` - delete attachment by id
- `DELETE api/attachments/all/{taskId}` - delete all attachments related to the task

## Installation

## ⌛ History of project creation ⌛

Creating this project, as it was a team effort, the biggest challenge was synchronizing the team as a cohesive entity. However, this hurdle was successfully overcome thanks to the initiative and resourcefulness of all team members. From a technological standpoint, the most challenging, yet intriguing, aspects were integrating notifications via the Telegram messenger and utilizing the Dropboxdrb box service for storing attachments. Nevertheless, this milestone was also successfully conquered.💪

## 📈 Possible improvements 📈

Overall, we are confident that this project covers all necessary functionality and is sufficiently convenient for integration. However, as always, there is room for growth. First and foremost, shortly, our plans include achieving maximum code coverage with tests, adding the ability to search for projects/tasks by partial name or other fields, as well as expanding the functionality of notifications via Telegram
<div style="width:100%; text-align:center;">
    <img src="images/readme_footer_image.gif" alt="Footer Image">
</div>