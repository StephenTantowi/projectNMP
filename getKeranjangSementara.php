<?php 
	error_reporting(E_ERROR | E_PARSE);
	$c = new mysqli("localhost", "nmp160418024", "ubaya", "nmp160418024");

	if($c->connect_errno){
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}

	if($_POST['iduser'])
	{
		$iduser = $_POST['iduser'];
		$sql = "SELECT ks.*, p.* FROM keranjangsementara ks INNER JOIN product p ON ks.idproduct = p.idproduct WHERE ks.iduser = '$iduser'";
		$result = $c->query($sql);
		$array = array();

		if ($result->num_rows > 0) 
		{
			while ($obj = $result->fetch_object())
			{
				$array[]  = $obj;			
			}

			$arr = array("result" => "OK", "data" => $array, "message" => "get keranjangsementara");
			echo json_encode($arr);
		}
		else
		{
			$arr = array("result" => "ERROR", "message" => "No Data Found");
			echo json_encode($arr);
		}
	}
	else
	{
		$arr = array("result" => "ERROR", 
			"message" => "error");
		echo json_encode($arr);
	}
 ?>