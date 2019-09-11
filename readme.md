# Create a code snippet -app

App where you can store all snippets/notes what you might consider useful in the one place!

##### Features:
- encrypted 'secureCode' login for trusted users
- basic crud -operations for snippets
- Option to make snippet private/visible for other users
- html tag support in snippets
- responsive design
- token requirement for api-calls
- snippet-topic search option

## Used technologies:
#### frontend:
- Vue.js (3 cli)
- Vuex (centralized state management)
- Vuetify (components & styling)

#### backend:
- Kotlin
- Spring-boot
- Maven

#### database: 
- MYSQL


## Deployment:
Currently deployed to google cloud services (gke) using docker-containers, ingress-loadbalancing, hpa (horizontal pod autoscaling) and custom node-pool.

<b>frontend:</b> google kubernetes engine   
<b>backend:</b> google kubernetes engine   
<b>db:</b> google cloud sql  

<b>other:</b>   
google build-triggers when changes are pushed to master (github)


Url: <a href="http://34.102.153.220/">save-a-code-snippet-app</a>
