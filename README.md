# EFG_Shien


#To Run the project using CMD :
- Go to test.properties and update chromedriver path with your own chrome driver path
- Navigate to the main directory which contains gradlew.bat and open cmd then type "gradlew clean test"

#To run using jenkins :
- choose freestyle project 
- General : github project -> put the link of this Repo
- source code managment :  git -> put the link of this Repo.git
- Build : choose Invoke gradle scripts -> Tasks : clean test 
