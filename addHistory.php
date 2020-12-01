<?php
	error_reporting(E_ERROR | E_PARSE);
	$c = new mysqli("localhost", "nmp160418024", "ubaya", "nmp160418024");

	if($c->connect_errno) 
	{
		$arr = array("result" => "ERROR", 
					 "message" => "Failed to Connect");
		echo json_encode($arr);
		die();
	}

	//Cek data yang dikirim
	if($_POST['iduser']) 
	{
		$iduser = $_POST['iduser'];

		//Copy data tabel keranjangsementara -> history

		$sql1 = "INSERT INTO history(iduser, idproduct, jumlah, totalHarga) 
				SELECT iduser, idproduct, jumlah, totalHarga FROM keranjangsementara WHERE iduser = '$iduser'";

		$c->query($sql1);
		$arr1 = array("result" => "OK", 
		"sql"	=> $sql1,
		"message" => "History added");
		echo json_encode($arr1);

		//Hapus data tabel keranjangsementara
		$sql2 = "DELETE FROM keranjangsementara WHERE iduser = '$iduser'";
		$c->query($sql2);
		$arr2 = array("result" => "OK", 
		"sql"	=> $sql2,
		"message" => "keranjangsementara deleted");
		echo json_encode($arr2);
	} 
	else 
	{
		$arr = array("result" => "ERROR", 
			"message" => "error");
		echo json_encode($arr);
	}
?>