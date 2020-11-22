<?php 
	error_reporting(E_ERROR | E_PARSE);
	$con = new mysqli("localhost", "root", "","projectnmp");

	if($con->connect_errno){
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}


	if($_POST['password'] && $_POST['nama'] && $_POST["iduser"]) 
	{
		$iduser = $_POST['iduser'];
		$password = $_POST['password'];
		$nama = $_POST['nama'];
		$sql = "UPDATE user SET nama = '$nama', password = '$password' WHERE iduser = $iduser";
		$con->query($sql);

		$arr = array("result" => "OK", 
			"sql"	=> $sql,
			"message" => "user updated");
		echo json_encode($arr);
	} 
	elseif ($_POST['nama'] && $_POST["iduser"]) 
	{
		$iduser = $_POST['iduser'];
		$nama = $_POST['nama'];
		$sql = "UPDATE user SET nama = '$nama' WHERE iduser = $iduser";
		$con->query($sql);

		$arr = array("result" => "OK", 
			"sql"	=> $sql,
			"message" => "user updated");
		echo json_encode($arr);
	}
	else
	{
		$arr = array("result" => "ERROR", 
			"message" => "data is empty");
		echo json_encode($arr);
	}
 ?>