<?php 
	error_reporting(E_ERROR | E_PARSE);
	$con = new mysqli("localhost", "root", "","projectnmp");

	if($con->connect_errno){
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}

	$test = $_POST["iduser"];
	$sqlCekPass = "SELECT password FROM user WHERE iduser = $test";
	$hasil = $con->query($sqlCekPass);
	$hasilobj = mysqli_fetch_object($hasil);
	$pass = $hasilobj->password;
	

	if("" == trim($_POST['oldPassword']) && "" == trim($_POST['newPassword']) && $_POST["iduser"] && $_POST['nama']) 
	{
		$iduser = $_POST['iduser'];
		$nama = $_POST['nama'];
		
		$sql = "UPDATE user SET nama = '$nama' WHERE iduser = $iduser";
		$con->query($sql);

		$arr = array("result" => "OK", 
			"sql"	=> $sql,
			"message" => "nama updated");
		echo json_encode($arr);
	} 
	elseif ("" == trim($_POST['nama']) && $_POST["iduser"] && $_POST['oldPassword'] && $_POST['newPassword']) 
	{
		$iduser = $_POST['iduser'];
		$oldPassword = $_POST['oldPassword'];
		$newPassword = $_POST['newPassword'];

		if($oldPassword == $pass)
		{
			$sql = "UPDATE user SET password = '$newPassword' WHERE iduser = $iduser";
			$con->query($sql);

			$arr = array("result" => "OK", 
			"sql"	=> $sql,
			"message" => "password updated");
			echo json_encode($arr);
		}
		else
		{
			$arr = array("result" => "ERROR", 
			"message" => "password tidak sama");
			echo json_encode($arr);
		}
		
	}
	elseif ($_POST['newPassword'] && $_POST['oldPassword'] && $_POST["iduser"] && $_POST['nama']) 
	{
		$iduser = $_POST['iduser'];
		$oldPassword = $_POST['oldPassword'];
		$newPassword = $_POST['newPassword'];
		$nama = $_POST['nama'];

		if($oldPassword == $pass)
		{
			$sql = "UPDATE user SET nama = '$nama', pasword = '$newPassword' WHERE iduser = $iduser";
			$con->query($sql);

			$arr = array("result" => "OK", 
				"sql"	=> $sql,
				"message" => "nama & password updated");
			echo json_encode($arr);
		}
		else
		{
			$arr = array("result" => "ERROR", 
			"message" => "password tidak sama");
			echo json_encode($arr);
		}
	}
	else
	{
		$arr = array("result" => "ERROR", 
			"message" => "data is empty");
		echo json_encode($arr);
	}
 ?>