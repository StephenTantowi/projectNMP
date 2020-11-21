<?php 
	error_reporting(E_ERROR | E_PARSE);
	$con = new mysqli("localhost", "root", "","projectnmp");

	if($con->connect_errno){
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}


if($_POST['email'] && $_POST['password'] && $_POST['nama'] && $_POST['iduser']) {
		$iduser = $_POST['iduser'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		$nama = $_POST['nama'];
		$sql = "UPDATE user SET nama = '$nama', email = '$email', password = '$password' WHERE iduser = $iduser";
		$con->query($sql);

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