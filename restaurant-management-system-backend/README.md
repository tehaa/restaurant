

## the restaurant database must be imported before running the back-end

## first step

 run  `mvn clean install` 

##second step

run `nohup java -jar target/restaurant-management-system-0.0.1.jar > restaurant.log &`

##make sure project run successfully 

run `tail -f restaurant.log` to view logs 

##start using application if frontend is running 

1-open your Browser
2-go to http://localhost:4200/ 

3- account for user view :
 
email: mohamed
password: 12345

4-account for admin view

email: ahmed
password: 12345



