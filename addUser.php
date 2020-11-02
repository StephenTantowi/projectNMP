<?php
	error_reporting(E_ERROR | E_PARSE);
	$c = new mysqli("localhost", "root","","projectnmp");


	if($c->connect_errno) {
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}
   if($_POST['email'] && $_POST['password'] && $_POST['nama']) {
		$email = (String) $_POST['email'];
		$password = (String) $_POST['password'];
		$nama = (String) $_POST['nama'];
		$sql = "INSERT INTO user(email,password, nama) values ('$email', '$password', '$nama')";
		$c->query($sql);
		$arr = array("result" => "OK", 
			"sql"	=> $sql,
			"message" => "user added");
		echo json_encode($arr);
	} else {
		$arr = array("result" => "ERROR", 
			"message" => "data is empty");
		echo json_encode($arr);
	}
?>