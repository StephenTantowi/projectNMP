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
	if($_POST['iduser'] && $_POST['idproduct'] && $_POST['aktivitas']) 
	{
		$iduser = $_POST['iduser'];
		$idproduct = $_POST['idproduct'];
		$aktivitas = $_POST['aktivitas'];

		//Cek data aktivitas

		//Kalau ada
		if($aktivitas == "Tambah")
		{
			//Ambil harga dari tabel product
			$sql2 = "SELECT harga from product where idproduct = '$idproduct'";
			$hasil2 = $c->query($sql2);
			$hasilobj2 = mysqli_fetch_object($hasil2);
			$harga = $hasilobj2->harga;

			//Update keranjangsementara
			$sql1 = "UPDATE keranjangsementara SET jumlah = jumlah + 1, totalHarga = totalHarga + $harga WHERE idproduct = '$idproduct' && iduser = '$iduser'";

			$c->query($sql1);
			$arr1 = array("result" => "OK", 
				"sql"	=> $sql1,
				"message" => "keranjangsementara updated tambah");
			echo json_encode($arr1);
		}
		//Kalau tidak
		else if($aktivitas == "Kurang")
		{
			//Ambil harga dari tabel product
			$sql2 = "SELECT harga from product where idproduct = '$idproduct'";
			$hasil2 = $c->query($sql2);
			$hasilobj2 = mysqli_fetch_object($hasil2);
			$harga = $hasilobj2->harga;

			//Update keranjangsementara
			$sql1 = "UPDATE keranjangsementara SET jumlah = jumlah - 1, totalHarga = totalHarga - $harga WHERE idproduct = '$idproduct' && iduser = '$iduser'";

			$c->query($sql1);
			$arr1 = array("result" => "OK", 
				"sql"	=> $sql1,
				"message" => "keranjangsementara updated kurang");
			echo json_encode($arr1);
		}
		else
		{

		}		
	} 
	else 
	{
		$arr = array("result" => "ERROR", 
			"message" => "error");
		echo json_encode($arr);
	}
?>