//https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#textual-template-modes
console.log('cache? 1');


var username = [[${name}]]; //两个[[  中文变成 \uxxxx自动增加" "
console.log('from javascript template hello :'+username);

[# th:if="${name}"]
console.log('have name attr');
[/]

[# th:each="item : ${hobbys}"]    
	console.log([[${item.name}]] + " " + [[${item.value}]]);
[/]


[# th:if="${allEmps.size() lt 3}"]
	console.log('lt 3');
[/]
[# th:if="${allEmps.size() gt 3}"]
	console.log('gt 3');
[/] 

/*[+
	alert('this is normal code ,only for javascript and css');
+]*/

	
/*[- */
	alert('this is comment code ,only for TEXT and javascript and css');
/* -]*/
 
console.log(  /*  [[${name}]]  */ "inline comment test, only for javascript and css ");

/*[# th:if="${namexx}"]*/
	console.log('comment test , only for javascript and css ');
/*[/]*/

[# th:if="${namexx}"]
	console.log('namexx');
[/] 



	
	
	
	
	
	
	
	
	
	

