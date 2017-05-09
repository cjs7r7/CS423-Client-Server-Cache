# CS423-Client-Server-Cache

This is the final project for CS 423: Client-Server Architecture.

The project assignment is the ProjectCS423.docx file.

This project is three parts:

1) An apache web server running on a geni instance ( connected using ssh tunnel)

2) A cache server that will request files from the apache server and cache them upon client request.

3) A client that will send requests to the cache server.



This assignment is to show multithreading and http headers using plain java sockets.


Note: this is the ssh command to setup cache server to geni tunnel securely:
ssh -p 38715 -L 8080:localhost:80 -N cjsy3c@pc4.instageni.gpolab.bbn.com