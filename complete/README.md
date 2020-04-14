
Dev Tools - Mini Profiler UI:
    
    Display detailed requests on UI as they go through different applications
    
    Graph of all requests flowing through different applications
     For each application
     Log incoming and outgoing requests timeseries, latest on top
     
      For each Http request:
         Log method stack, or detailed JSON serialized arguments
         Jump to IntelliJ on clicking logs
             Method level arguments, values
               DB input sql, input parameters, output
      And its Http response
      
    Exception stack Method level arguments, values
    
    Compare Http Request body JSON/serialized Resource Request body
    
    Messaging outputs
        JMS
        Kafka 
    
    Outgoing Http Requests/Responses
        Stub outgoing requests/responses

    Export requests to Postman, Curl, ...
    
    Persist/Compare previous requests
