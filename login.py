# -*- coding: utf-8 -*-
"""
Created on Wed Aug 15 17:04:57 2018

@author: Ankur
"""
import pymysql 
import pymysql.cursors
from flask import Flask, redirect, url_for, request, render_template
app = Flask(__name__)
con = pymysql.connect(user="root", password="admin", host="localhost",port=3306,db="demo")
sql = "SELECT * from student"
try:
    with con.cursor() as cursor:
        # Create a new record
        sql = "select * from student"
        cursor.execute(sql)
        result = cursor.fetchall()
    # connection is not autocommit by default. So you must commit to save
    # your changes.
    con.commit()
finally:
    con.close()

@app.route('/')
def hello_world():
   return render_template("success.html",data = result, le =len(result) )



@app.route('/login',methods = ['POST', 'GET'])
def login():
   if request.method == 'POST':
      user = request.form['nm']
      return render_template("success.html",data = result, le =len(result) )
   else:
      user = request.args.get('nm')
      return render_template("success.html",data = result, le =len(result) )
  
@app.route('/search',methods = ['POST', 'GET'])
def search():
    return render_template("index.html",data = result, le =len(result) )

if __name__ == '__main__':
   app.run(debug = True)