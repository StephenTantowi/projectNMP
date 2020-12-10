<?php
	error_reporting(E_ERROR | E_PARSE);
	$c = new mysqli("localhost", "root","","projectnmp");


	if($c->connect_errno) {
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}
   if(trim($_POST['email']) != "" && trim($_POST['password']) != "" && trim($_POST['nama']) != "" && trim($_POST['ulangipassword']) != "") 
   {
		$email = $_POST['email'];
		$password = $_POST['password'];
		$nama = $_POST['nama'];
		$ulangipassword = $_POST['ulangipassword'];

		if($password == $ulangipassword)
		{
			$sql = "SELECT * from user WHERE email = '$email'";
			$hasil = $c->query($sql);
			$hasilobj = mysqli_fetch_object($hasil);
			$cekEmail = $hasilobj->email;

			if(is_null($cekEmail))
			{
				$sql2 = "INSERT INTO user(email,password, nama) values ('$email', '$password', '$nama')";
				$c->query($sql2);
				$arr2 = array("result" => "OK", 
					"sql"	=> $sql2,
					"message" => "user added");
				echo json_encode($arr2);
			}
			else
			{
				$arr = array("result" => "ERROR", 
					"message" => "email telah digunakan");
				echo json_encode($arr);
			}
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