# TASK MANAGEMENT SYSTEM

## 👋 Introduction 👋

This is a simple and convenient app for managing tasks through projects.   
It lets users create and follow up on task progress with integrated notifications via Telegram messenger.

## 👩‍💻Technology Stack 👩‍💻
- Programming Language: Java
- Application Configuration: Spring Boot, Spring, Maven
- Accessing Data Spring Data: JPA, Hibernate, MySQL, PostgreSQL
- Web Development: Spring MVC, Servlets, Tomcat
- Testing and Documentation: JUnit, Mockito, Swagger, TestContainers
- Version Control: Git
- Infrastructure: Docker
- Database migration: Liquibase


## 🚀 Functionality 🚀
**click the description to try out the request in Postman**  
You can proceed directly with the login endpoint as there is an admin user in the DB.  
Also, it's supposed that a project must be created first, then a task and other entities.

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
- `POST api/tasks` - [create a new task for a project](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-566d2ff4-f543-40ca-8db0-12e16f30e787?action=share&creator=31607463&ctx=documentation)
- `GET api/tasks`- [get all tasks details by project ID](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-66eb0f39-8ae8-4444-a538-3e20cd3f4e9d?action=share&creator=31607463&ctx=documentation)
- `GET api/tasks{id}` - [get task details by task ID](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-12c2e4f0-e940-45b7-ba4c-971b2cc3c5b9?action=share&creator=31607463&ctx=documentation)
- `PUT api/tasks/{id}` - [update task details by task ID](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-4edb0ca2-bbee-4b55-a245-3fe42393cc80?action=share&creator=31607463&ctx=documentation)
- `DELETE api/tasks/{id}` - [delete task by ID](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-99fa08bf-13af-4c26-adfc-826160754245?action=share&creator=31607463&ctx=documentation)

### 5. **Label Controller**
- `POST api/labels` - [create new label](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-59465d21-78a9-469c-b17c-c6ca8758b9f5?action=share&creator=31607463&ctx=documentation)
- `GET api/labels/by_project/{projectId}` - [get all labels within the project](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-86d98d12-cc98-4b60-9f2c-c412f50ec04f?action=share&creator=31607463&ctx=documentation)
- `PUT api/labels/{id}` - [update label](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-f1a46f64-0f0a-4ec6-a9d5-7059e07cb2ed?action=share&creator=31607463&ctx=documentation)
- `DELETE api/labels/{id}` - [delete label by id](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-09038779-c189-41ae-8eab-ea1fafb46f62?action=share&creator=31607463&ctx=documentation)

### 6. **Comment Controller**:
- `POST api/comments/{taskId}` - [add a new comment to a task by task ID](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-5fd8d603-07e2-4393-9c5c-b4fe1289f369?action=share&creator=31607463&ctx=documentation)
- `GET api/comments/{taskId}` - [retrieve comments within a task](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-e03dcde6-74a7-4d1a-ac86-cb306495f063?action=share&creator=31607463&ctx=documentation)
- `PUT api/comments/{id}` - [update a comment](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-9869c5f6-ec0f-4b70-88e0-bca2850b20e5?action=share&creator=31607463&ctx=documentation)
- `DELETE api/comments/{id}` - [delete a comment by ID](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-f3f7b036-d391-488d-b147-f9c460af0aa9?action=share&creator=31607463&ctx=documentation)

### 7. **Attachment Controller**:
- `POST api/attachments` - [create an attachment](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-a7667407-e0a4-432a-8862-a6ed9df413e1?action=share&creator=31607463&ctx=documentation)
- `GET api/attachments/{id}` - [get attachment details by ID](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-eab19b37-47fe-43f6-abaf-fc58115e77c3?action=share&creator=31607463&ctx=documentation)
- `DELETE api/attachments/{id}` - [delete attachment by id](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-8f7c7aed-bc04-49c2-a49c-9d0f893ea7a1?action=share&creator=31607463&ctx=documentation)
- `DELETE api/attachments/all/{taskId}` - [delete all attachments related to the task](https://api-test-3019.postman.co/workspace/Public-team-project-workspace~28f82006-8ec0-422a-b26a-d303ea6538ac/request/31607463-ca21105a-b127-4712-8053-8102cebe6087?action=share&creator=31607463&ctx=documentation)

## Installation

## ⌛ History of project creation ⌛

Creating this project, as it was a team effort, the biggest challenge was synchronizing the team as a cohesive entity.   
However, this hurdle was successfully overcome thanks to the initiative and resourcefulness of all team members.   
From a technological standpoint, the most challenging, yet intriguing, aspects were integrating notifications via   
the Telegram messenger and utilizing the Dropboxdrb box service for storing attachments.   
Nevertheless, this milestone was also successfully conquered.💪

## 📈 Possible improvements 📈

Overall, we are confident that this project covers all necessary functionality and is sufficiently   
convenient for integration. However, as always, there is room for growth. First and foremost, shortly, our plans include  
achieving maximum code coverage with tests, adding the ability to search for projects/tasks by partial name or   
other fields, as well as expanding the functionality of notifications via Telegram

## 💪 TEAM 💪
[Yevhenii Vlasenko](https://www.linkedin.com/in/yevhenii-vlasenko-776064218/)  
[Valentyn Sharshon](https://www.linkedin.com/in/valentyn-sharshon-6b9339259/)  
[Kateryna Makarchuk](https://www.linkedin.com/in/kateryna-makarchuk-a89bab217/)  
[Bohdan Bohush](https://github.com/bodya4243)  
[Bohdan Markatov](https://github.com/Bohadan-Markatov)  
[Eduard Fakhrutdinov](https://github.com/EduarfF)
<div style="width:100%; text-align:center;">
    <img src="images/readme_footer_image.gif" alt="Footer Image">
</div>