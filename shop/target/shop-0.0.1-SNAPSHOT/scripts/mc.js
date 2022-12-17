/**
 * 
 */
 function validate(){ 
	var ok = true; 
 	var p = document.getElementById("principal").value; 
 	if (isNaN(p) || p <= 0) { 
 		alert("Principal invalid!"); 
 		document.getElementById("principal-error").style.display = "inline"; 
 		document.getElementById("principal-error").style.color = "red"; 
 		ok = false; 
 	} 
 	else { 
 		document.getElementById("principal-error").style.display = "none"; 
 	} 
 
 	p = document.getElementById("interest").value; 
 	if (isNaN(p) || p < 0 || p > 100) { 
 		alert("Invalid. Must be in (0,100)."); 
 		document.getElementById("interest-error").style.display = "inline"; 
 		document.getElementById("interest-error").style.color = "red"; 
	 	ok = false; 
 	} 
 	else { 
 		document.getElementById("interest-error").style.display = "none"; 
 	}
 	
 	p = document.getElementById("period").value; 
 	if (isNaN(p) || p <= 0) { 
 		alert("Period invalid!"); 
 		document.getElementById("period-error").style.display = "inline"; 
 		document.getElementById("period-error").style.color = "red"; 
	 	ok = false; 
 	} 
 	else { 
 		document.getElementById("period-error").style.display = "none"; 
 	}
 	if (!ok) {
		return false;
	}  
 	return ok; 
 }
 
 function reportAjax(address){
 	var request = new XMLHttpRequest();
 	var data="";
 	/* add your code here to grab all parameters from form*/
	data += "surname=" + document.getElementById("surname").value + "&";
 	request.open("GET", (address + "?" + data), true);
 	request.onreadystatechange = function() {
 		handler(request);
 	};
 	request.send(null);
 }
 
 function handler(request){
 	if ((request.readyState == 4) && (request.status == 200)){
 	var target = document.getElementById("result");
 	target.innerHTML = request.responseText;
 	//here you want to add parse the json and display individual key,
 	//values pairs as html elements ( tables, paragraphs, etc..)
 	var rs=JSON.parse(request.responseText);
 	addParagraphs(target, rs);
 }
}


function addParagraphs(parent, rs){
	//parent.innerHTML += "<br/><br/>" + "Principal:" + p1 + "<br/>";
	//parent.innerHTML += "<br/>" + "Monthly Payment:" + p2;
	//extract the table header
	let col = [];
    for (let i = 0; i < rs.length; i++) {
      for (let key in rs[i]) {
        if (col.indexOf(key) === -1) {
          col.push(key);
        }
      }
    }
    //create table
    const table = document.createElement("table");
    //create table row
    let tr = table.insertRow(-1); 
    for (let i = 0; i < col.length; i++) {
      let th = document.createElement("th");      // insert table header.
      th.innerHTML = col[i];
      tr.appendChild(th);
    }
    //add json data to the table after the header row
    for (let i = 0; i < rs.length; i++) {
      tr = table.insertRow(-1);
      for (let j = 0; j < col.length; j++) {
        let tabCell = tr.insertCell(-1);
        tabCell.innerHTML = rs[i][col[j]];
      }
      //insert data to the <div>
    	parent.innerHTML = "";
    	parent.appendChild(table);
    }
}

function cart(address){
 	var request = new XMLHttpRequest();
 	var data="";
 	/* add your code here to grab all parameters from form*/
	data += "surname=" + document.getElementById("surname").value + "&";
 	request.open("GET", (address + "?" + data), true);
 	request.onreadystatechange = function() {
 		cartHandler(request);
 	};
 	request.send(null);
 }
 
 function cartHandler(request){
 	if ((request.readyState == 4) && (request.status == 200)){
 	var target = document.getElementById("cartResult");
 	target.innerHTML = request.responseText;
 	//here you want to add parse the json and display individual key,
 	//values pairs as html elements ( tables, paragraphs, etc..)
 	var rs=JSON.parse(request.responseText);
 	addParagraphs1(target, rs);
 }
}

function addParagraphs1(parent, rs){
	//parent.innerHTML += "<br/><br/>" + "Principal:" + p1 + "<br/>";
	//parent.innerHTML += "<br/>" + "Monthly Payment:" + p2;
	//extract the table header
	let col = [];
    for (let i = 0; i < rs.length; i++) {
      for (let key in rs[i]) {
        if (col.indexOf(key) === -1) {
          col.push(key);
        }
      }
    }
    //create table
    const table = document.createElement("table");
    //create table row
    let tr = table.insertRow(-1); 
    for (let i = 0; i < col.length; i++) {
      let th = document.createElement("th");      // insert table header.
      th.innerHTML = col[i];
      tr.appendChild(th);
    }
    //add json data to the table after the header row
    for (let i = 0; i < rs.length; i++) {
      tr = table.insertRow(-1);
      for (let j = 0; j < col.length; j++) {
        let tabCell = tr.insertCell(-1);
        tabCell.innerHTML = rs[i][col[j]];
      }
      //insert data to the <div>
    	parent.innerHTML = "";
    	parent.appendChild(table);
    }
}