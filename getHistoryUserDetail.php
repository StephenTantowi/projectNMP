<?php 
	error_reporting(E_ERROR | E_PARSE);
	$con = new mysqli("localhost", "nmp160418024", "ubaya","nmp160418024");

	if($con->connect_errno){
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}

	if($_POST['iduser'] && $_POST['orderId'])
	{
		$iduser = $_POST['iduser'];
		$orderId = $_POST['orderId'];

		$sql = "SELECT h.*, p.* FROM history h inner join product p on h.idproduct = p.idproduct WHERE h.iduser = '$iduser' AND h.orderId = '$orderId'";
		$result = $con->query($sql);
		$array = array();

		if ($result->num_rows > 0) 
		{
			while ($obj = $result->fetch_object())
			{
				$array[]  = $obj;			
			}

			$arr = array("result" => "OK", "data" => $array);
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
		$arr = array("result" => "ERROR", "message" => "No Data Found");
		echo json_encode($arr);
	}
 ?>