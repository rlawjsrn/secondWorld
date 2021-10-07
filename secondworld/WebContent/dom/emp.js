let xhtp = new XMLHttpRequest();
xhtp.onload = function () {
	let data = JSON.parse(xhtp.responseText);
	showEmpList(data);
}
xhtp.open('get', '../empJsonServlet.json');
xhtp.send();

function showEmpList(data) {

	let table = document.createElement('table');
	table.setAttribute('border', '1');

	// 타이틀 생성.
	table.appendChild(getTitle());

	// 데이터 생성.
	for (let row of data) {
		table.appendChild(getRow(row));
	}

	// div 에 보여주기.
	document.getElementById('show').appendChild(table);
}

function getRow(row) { // 데이터 한건 처리.
	let tr, td;
	tr = document.createElement('tr');
	tr.onclick = function(e) {
		console.log(this,e);
		document.getElementById('empId').value = this.childNodes[0].innerHTML; //id값
		document.getElementById('name').value = this.childNodes[1].innerHTML;
		document.getElementById('email').value = this.childNodes[2].innerHTML;
		document.getElementById('hire').value = this.childNodes[3].innerHTML;
		document.getElementById('job').value = this.childNodes[4].innerHTML;
				
	}
	for (let field in row) {
		td = document.createElement('td');
		td.appendChild(document.createTextNode(row[field]));
		tr.appendChild(td);
	}
	// 삭제버튼추가
	let btn = document.createElement('button');
	btn.onclick = function (e) {
		e.stopPropagation();// button < td < tr < table < body 이벤트를 button만 실행하고 위의 애들은 실행하지 않게 하겠다.(?)
		let id = this.parentNode.parentNode.firstChild.innerHTML;
		let tr = this.parentNode.parentNode;
		//삭제 서블릿. call - ajax이용
		let xhtp = new XMLHttpRequest();
		xhtp.onload = function () {
			console.log(xhtp.responseText);
			let result = JSON.parse(xhtp.responseText);
			if (result.retCode == 'success') {
				tr.remove();
			} else {
				window.alert('처리중 에러발생');
			}
		}
		xhtp.open('get', '../DelEmpServlet?delId=' + id); //DelEmpServlet을 호출 할 때의 파라미터이름(delId)을 id로 받겠다.
		xhtp.send();
	}
	btn.innerHTML = '삭제';
	td = document.createElement('td');
	td.appendChild(btn);
	tr.appendChild(td);

	return tr;
}

function getTitle() { // 헤더의 타이틀 생성.
	let titles = ['사원번호', '이름', '이메일', '입사일자', '직무', '기능'];
	let tr, td;
	tr = document.createElement('tr');
	for (let i = 0; i < titles.length; i++) {
		td = document.createElement('th');
		td.appendChild(document.createTextNode(titles[i]));
		tr.appendChild(td);
	}
	return tr;
}