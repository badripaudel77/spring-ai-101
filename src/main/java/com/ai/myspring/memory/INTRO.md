### By default chats are stateless.
### For example if I prompt before as : My Name is Badri" but in the later prompt
    if I ask what is my name, it won't say cause it doesn't have access to data.

With Chat Memory, it will learn the context and store the data throughout the conversation. 

For example, without chat memory :
http://localhost:8080/api/memory/chats?prompt=my name is Badri Paudel ?
http://localhost:8080/api/memory/chats?prompt=What is my name ?

It won't be able to tell as conversation is stateless.
However, if we configure memory, it will be able to know it.