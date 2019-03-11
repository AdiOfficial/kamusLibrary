package com.example.renosyahputra.librarykamus

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.renosyahputra.localdatakamuslib.model.KosakKataModel
import com.example.renosyahputra.localdatakamuslib.model.ListKosakKataModel
import com.example.renosyahputra.localdatakamuslib.queryKata.QueryKata
import com.example.renosyahputra.localdatakamuslib.tambahKata.TambahKata
import com.syahputrareno975.textscannerlib.TextScannerInit
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),
        View.OnClickListener,
        QueryKata.OnQueryKataListener,
        TambahKata.OnTambahKataListener,
        TextScannerInit.OnTextScannerInitListener {



    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidget()
    }

    private fun initWidget(){
        context = this@MainActivity

        buttonCari.setOnClickListener(this)
        buttonScan.setOnClickListener(this)

        findBy.text = QueryKata.Indonesia
        findBy.setOnClickListener(this)

        setDefaultResources()
    }

    override fun onClick(v: View?) {
        when (v){
            buttonCari -> {

                QueryKata.newInstance()
                        .setContext(context)
                        .setFindBy(findBy.text.toString())
                        .setKosakKataDicari(KosakKataModel(TextDicari.text.toString(), TextDicari.text.toString()))
                        .setOnQueryKataListener(this)
                        .cari()
            }

            findBy -> {
                findBy.text = if (findBy.text.toString() == QueryKata.Indonesia) QueryKata.Inggris else QueryKata.Indonesia
            }

            buttonScan -> {

                TextScannerInit.newInstance()
                        .setContext(context)
                        .setOnTextScannerInitListener(this)
                        .scanText()


            }
        }
    }

    override fun onListKataFound(kosakKataModels: ArrayList<KosakKataModel>) {
        var hasil_text = ""
        for (data in kosakKataModels) {
            hasil_text += "Bahasa Indonesia : ${data.BahasaIndonesia}\n"
            hasil_text += "keterangan : ${data.keteranganBahasaIndonesia}\n\n"
            hasil_text += "Bahasa Inggris : ${data.BahasaInggris}\n"
            hasil_text += "Description : ${data.keteranganBahasaInggris}\n\n"
        }
        hasil.text = hasil_text
    }

    override fun onScannerFinish() {
        Toast.makeText(context,"Scanner finish!",Toast.LENGTH_SHORT).show()
    }


    override fun onGetListString(texts: ArrayList<String>) {

        for (data in texts) {

            QueryKata.newInstance()
                    .setContext(context)
                    .setFindBy(findBy.text.toString())
                    .setKosakKataDicari(KosakKataModel(data, data))
                    .setOnQueryKataListener(this)
                    .cari()
        }
    }

    override fun onErrorLogs(errors: ArrayList<String>) {
        Toast.makeText(context,errors.toString(),Toast.LENGTH_SHORT).show()
    }

    private fun setDefaultResources(){
        val listKosakKataModel = ListKosakKataModel()
        listKosakKataModel.kosakKataModels.add(KosakKataModel("me","which mean person it self","saya","artinya adalah orang pertama tunggal"))
        listKosakKataModel.kosakKataModels.add(KosakKataModel("car","some 4 wheele vehicle","mobil","kendaraan roda dua"))
        listKosakKataModel.kosakKataModels.add(KosakKataModel("box","kardus"))
        listKosakKataModel.kosakKataModels.add(KosakKataModel("duck","bebek"))
        listKosakKataModel.kosakKataModels.add(KosakKataModel("cat","kucing"))
        listKosakKataModel.kosakKataModels.add(KosakKataModel("dog","anjing"))

        TambahKata.newInstance()
                .setContext(context)
                .setListKosakKataModel(listKosakKataModel)
                .setOnTambahKataListener(this)
                .tambah()
    }

    override fun onBerhasilTambahKata(success: Boolean) {

    }
}
