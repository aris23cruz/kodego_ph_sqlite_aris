package com.kodego.inventory.app.sqliteexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodego.inventory.app.sqliteexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var databaseHelper = DatabaseHelper(applicationContext)
        var everyEmployee : MutableList<EmployeeModel> = databaseHelper.getAllData()
        var adapter = EmployeeAdaptor(everyEmployee)
        adapter.onItemDelete = { item:EmployeeModel, position: Int ->
            //delete from table
            var databaseHelper = DatabaseHelper(applicationContext)
            databaseHelper.deleteOne(item)
            //delete from recyclerview
            adapter.employeeModel.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnSave.setOnClickListener(){
            lateinit var employeeModel: EmployeeModel

            try {
                var name = binding.etName.text.toString()
                var salary = binding.etSalary.text.toString().toInt()
                //create new employee object
                employeeModel = EmployeeModel(-1,name,salary)
                //add new employee to recyclerview
                adapter.employeeModel.add(employeeModel)
                adapter.notifyDataSetChanged()
                //add new employee to database
                var databaseHelper = DatabaseHelper(applicationContext)
                databaseHelper.addOne(employeeModel)
                Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
            }catch(e: Exception){
                Toast.makeText(applicationContext,"Error Occurred",Toast.LENGTH_SHORT).show()
            }

        }
//        binding.btnView.setOnClickListener(){
//            var databaseHelper = DatabaseHelper(applicationContext)
//            var everyEmployee : List<EmployeeModel> = databaseHelper.getAllData()
//            Toast.makeText(applicationContext,everyEmployee.toString(),Toast.LENGTH_SHORT).show()
//        }
    }
}