
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
    Persist/Search/Compare previous requests
    Other profiler features: latency, execution times, statistics, hot spots 

Two Output Responses:
    1. all events of all types for the thread (tID) like a Tree model by timestamp like a profiler
    2. all events separated by event type by the thread ID (tID)?
    
How it works:
    Main class that does end to end tracing by the TraceID or TransactionID: 
    com.paycycle.util.devtools.helper.RequestTracer
        Tracing incoming Http Requests and their responses
             com.paycycle.util.devtools.filter.HttpLoggingFilter
        com.paycycle.util.devtools.helper.ThreadLocalHelper
             tracks methods for Aspects defined in com.paycycle.util.devtools.aspect.MethodTraceAspect
        com.paycycle.util.devtools.aspect.JDBCLogAspect
             tracks DB requests 
    All of them generate com.paycycle.util.devtools.Event implementations
    Getting the latest request
        
    
     
    
    