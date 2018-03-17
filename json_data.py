# -*- coding: utf-8 -*-
"""
Created on Sat Mar 17 07:21:52 2018

@author: Ankur
"""

from flask import Flask, render_template, jsonify
import json as json
import pandas
import numpy as np

app = Flask(__name__)

@app.route("/")
def index():
    df = pandas.DataFrame({
        "time" : [1,2,3,4,5],
        "temp" : np.random.rand(5)
        })
    d = [ dict([(colname, row[i]) 
        for i,colname in enumerate(df.columns)])
            for row in df.values
        ]
    return json.dumps(d)

if __name__ == '__main__':
   app.run(debug = True)