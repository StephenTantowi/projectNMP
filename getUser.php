<?php
	error_reporting(E_ERROR | E_PARSE);
	$con = new mysqli("localhost", "root", "","projectnmp");

	if($con->connect_errno){
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}

	

	if($_POST['email'] && $_POST['password'])
	{
		$email = $_POST['email'];
		$password = $_POST['password'];

		$sql = "SELECT * FROM user WHERE email = '$email' AND password = '$password'";

		$result = $con->query($sql);
		$array = array();

		if($result->num_rows > 0)
		{
			while($obj = $result->fetch_object())
			{
				$array[] = $obj;
			}
			$arr = array("result" => "OK", 
						 "data" => $array);
			echo json_encode($arr);
		}
		else
		{
			$arr = array("result" => "ERROR", 
						 "message" => "Email atau Password salah");
			echo json_encode($arr);
		}
	else
	{
		$arr = array("result" => "ERROR", 
					 "message" => "No Data Found");
		echo json_encode($arr);
	}

?>