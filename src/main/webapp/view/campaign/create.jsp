<h3>Kampány létrehozása</h3>
<p>A kampány foglalja össze egy keresés minden paraméterét.
	Kulcsszavakat és a találatok pontosságát leíró paramétereket. Meg
	ilyenek.</p>

<form method="post">
	<p>
		<label for="name">Név:</label> <input id="name" name="name"
			type="text" value="${name}" />
	</p>
	<p>
		<label for="dateFrom">Adatok kezdődátuma:</label> <input id="dateFrom"
			name="dateFrom" type="text" value="${dateFrom}" />
	</p>
	<p>
		<label for="keywords">Kulcsszavak vesszővel elválasztva:</label> <input
			id="keywords" name="keywords" type="text" value="${keywords}" />
	</p>
	<input type="submit" value="Mentés" />
</form>