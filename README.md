# Throttle Throttle My Threads


# Multiple users submit individual requests
i choose to have a ConcurrentHashMap 
  - the key will be the userId 
  - the value is an object that will mangae his own request
  
and like so every request that will be handle will be added to the write segment, and because it is a ConcurrentHashMap we will have lock on every segment it mean that a user can send 500 requests and in parralel second user can send 50 and both will be in separeted thread; 

like so we handle the first part every user will have a diferance process


now lets go more deep

  - a request wat handled
  - a thread "HandlerForUser" will start for the request
  - then we will check if it the user is registred in the map if yes it mean that he have more requests in process
  - if no we will add to the map with the vlaue of a new "RequestManager"
  - then we will add the request to the "weaitingRequest" list in the "RequestManager" by adding to a
  internal hashtable list that will stor the rate a key and the request a value
 
and now for the second fun part

    - we will synchronize a section on the userId request
    - then we calling the handellingRequest function
    the function will get the lowest rate for userId (the lowest is the most importent like in c++ mutex and staff....)
    the function will start running over the waitingRequest list for the current lowest rate
    and for each will do the task desired and move to done request list (for our nice printer therad) in general we don't need this he will incrace the stack memory
    - then will remove from the weiting request at the this rate
    and if the awaiting request list is not wmpty will recursively rerun the function 

# Tests
i sended a bulk of requests

    the 2 first request is for user 1 with rate of 2 and for rate 5;
    then i send a bulk of other requests for  deiferat users
    in middle i add more one request for the user 1 for rate of 1
    the n more bulk of users staff
    and then again for user 1 2 request one of rate 0 and one of rate 3
    
so for user 1 the follow rate was sended 2,5,1,0,3
    
the requests suppose to be handlled in the follow order 2 because its the first
    but because the thread of user 1 is blocked on the request with rate 3
    in paralle will be added to the list 5,1,0,3
    so the result will be 0 then 1 then 3 then 5
    so the ouput will be 2,0,1,3,5
    
 # classes  
Handler 

    the ConcurrentHashMap userId as key UserRequestManager as value
    the handleRequest here is our stating point reciving the request
    int the constrator a thread will run on the done requests so we can investigate what happen
MapHelper

    in the Constractor will start a new thread
    the run methode that will work on the user request
    
UserRequestManager

    an hashtable of the rate request a key and an list of requests as value
    an arraylist of done requests
    newRequest function will add to the waitinglist hashtable at the right rate
    getLowestRateTheMostImportent function running over the keys in the waiting list request hastable  and return the lowest value
    handleRequestInRateOrder here the task will produced
    

    
    
    




 
