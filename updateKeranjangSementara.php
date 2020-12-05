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
			$sql = "SELECT harga from product where idproduct = '$idproduct'";
			$hasil = $c->query($sql);
			$hasilobj = mysqli_fetch_object($hasil);
			$harga = $hasilobj->harga;

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
			$harga2 = $hasilobj2->harga;

			//Update keranjangsementara

			//Kurangi jumlah di DB
			$sql3 = "UPDATE keranjangsementara SET jumlah = jumlah - 1, totalHarga = totalHarga - $harga2 WHERE idproduct = '$idproduct' && iduser = '$iduser'";

			//Jalankan query pengurangan
			$c->query($sql3);
			$arr3 = array("result" => "OK", 
				"sql"	=> $sql3,
				"message" => "keranjangsementara updated kurang");
			echo json_encode($arr3);

			//Cek jumlah di DB
			$sql4 = "SELECT * FROM keranjangsementara WHERE idproduct = '$idproduct' && iduser = '$iduser'";
			$hasil4 = $c->query($sql4);
			$hasilobj4 = mysqli_fetch_object($hasil4);
			$jumlah4 = $hasilobj4->jumlah;

			//Kalau jumlah = 0, hapus column
			if($jumlah4 == 0)
			{
				$sql5 = "DELETE FROM keranjangsementara WHERE idproduct = '$idproduct' && iduser = '$iduser'";

				$c->query($sql5);
				$arr5 = array("result" => "OK", 
					"sql"	=> $sql5,
					"message" => "keranjangsementara updated hapus");
				echo json_encode($arr5);
			}
		}
		else 
		{
			$arr = array("result" => "ERROR", 
				"message" => "error");
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