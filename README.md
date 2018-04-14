# SubwayMapCalculus
This project could be done with a typical Dijkstra’s algorithm .  However I chose to go another route and instead 
optimize calculation time (CPU time) against mem time.  I did that by focusing on the Intersection type Node and adding (optimal) 
distance between all Intersection that (can) connects together.  This then makes it really easy to compute distance between 2 Nodes in 
a typical Subway Map.  My thinking is that unlike most Dijkstra’s map, a Subway Map  doesn't have that many 
Intersection (aka Nodes linked to more than 2 other Nodes). 

TODO :
- Update the Client (that's next!) : Right now I am using basic html form to interrogate the user and get the info needed.  I aim to 
change to a UIX...
- Add data persistency : Particularly on the map front, either by saving the map in file form (string based) or in non sql db (object based)
or even a sql + non sql DB mix
- Add security : By using Spring, we can now have robust authentification and allow file sharing (assuming data persistency is done)
