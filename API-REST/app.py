from flask import Flask
from flask import jsonify
from flask_restful import Resource, Api, reqparse
from flaskext.mysql import MySQL

app = Flask(__name__)
api = Api(app)
parser = reqparse.RequestParser()
mysql = MySQL()

# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'hqqmdit0b3lvs7f3'
app.config['MYSQL_DATABASE_PASSWORD'] = 'buk1r6n9xmot6p75'
app.config['MYSQL_DATABASE_DB'] = 'j1ygxmt6vf8xnto9'
app.config['MYSQL_DATABASE_HOST'] = 'a07yd3a6okcidwap.cbetxkdyhwsb.us-east-1.rds.amazonaws.com'
mysql.init_app(app)


#************************************************   TIMBRE   ********************************************************
class GetTimbre(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('ID', type=str, help='ID usuario')

            args = parser.parse_args()

            print(args.items())
            _ID = args['ID']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('getTimbre', (_ID))
            data = cursor.fetchall()
            print(data)
            if len(data[0])== 2:
                for item in data:
                    if item[1] == '1':
                        return jsonify(True)
                return jsonify(False)

            else:
                return jsonify(False);


        except Exception as e:
            return {'error': str(e)}

api.add_resource(GetTimbre, '/GetTimbre', methods=['POST'])

class UpdateTimbre(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('ID', type=str, help='ID usuario')
            parser.add_argument('Estado', type=str, help='Estado')
            args = parser.parse_args()

            print(args.items())
            _ID = args['ID']
            _Estado = args['Estado']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('updateTimbre', (_ID,_Estado))
            data = cursor.fetchall()

            print(data)
            if len(data) is 0:
                conn.commit()
                return jsonify(True)

            else:
                return data[0][0];


        except Exception as e:
            return {'error': str(e)}

api.add_resource(UpdateTimbre, '/UpdateTimbre', methods=['POST'])

#************************************************   TEMPERATURA   ********************************************************
class GetTemperatura(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('ID', type=str, help='ID usuario')

            args = parser.parse_args()

            print(args.items())
            _ID = args['ID']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('getTemperatura', (_ID))
            data = cursor.fetchall()
            print(data)
            if len(data[0]) == 4:
                for item in data:
                    if item[2] == '1':
                        return jsonify(item[1])
                return jsonify(False)
            else:
                return jsonify(False)



        except Exception as e:
            return {'error': str(e)}

api.add_resource(GetTemperatura, '/GetTemperatura', methods=['POST'])


class UpdateTemperatura(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('ID', type=str, help='ID usuario')
            args = parser.parse_args()

            print(args.items())
            _ID = args['ID']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('updateTemperatura', (_ID))
            data = cursor.fetchall()

            print(data)
            if len(data) is 0:
                conn.commit()
                return jsonify(True)

            else:
                return data[0][0];


        except Exception as e:
            return {'error': str(e)}

api.add_resource(UpdateTemperatura, '/UpdateTemperatura', methods=['POST'])


class AddTemperatura(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('ID', type=str, help='ID usuario')
            parser.add_argument('Dato', type=str, help='La temperatura')
            args = parser.parse_args()

            print(args.items())
            _ID = args['ID']
            _Dato = args['Dato']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('addTemperatura', (_ID,_Dato,'0'))
            data = cursor.fetchall()

            print(data)
            if len(data) is 0:
                conn.commit()
                return jsonify(True)

            else:
                return data[0][0];


        except Exception as e:
            return {'error': str(e)}

api.add_resource(AddTemperatura, '/AddTemperatura', methods=['POST'])


#************************************************   Luz   ********************************************************
class GetLuz(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('ID', type=str, help='ID usuario')

            args = parser.parse_args()

            print(args.items())
            _ID = args['ID']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('getLuz', (_ID))
            data = cursor.fetchall()
            print(data)
            if len(data[0])== 2:
                #items_list = [];
                for item in data:
                    i = {
                        'Estado': item[1],
                    }
                    #items_list.append(i)
                    return item[1]
                #return jsonify(items_list)
            else:
                return 0
                #return jsonify(False)
                #return data[0][0];
        except Exception as e:
            return {'error': str(e)}

api.add_resource(GetLuz, '/GetLuz', methods=['POST'])

class UpdateLuz(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('ID', type=str, help='ID usuario')
            parser.add_argument('Estado', type=str, help='Estado')
            args = parser.parse_args()

            print(args.items())
            _ID = args['ID']
            _Estado = args['Estado']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('updateLuz', (_ID,_Estado))
            data = cursor.fetchall()

            print(data)
            if len(data) is 0:
                conn.commit()
                return jsonify(True)

            else:
                return data[0][0];


        except Exception as e:
            return {'error': str(e)}

api.add_resource(UpdateLuz, '/UpdateLuz', methods=['POST'])

#************************************************   Puerta   ********************************************************
class GetPuerta(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('ID', type=str, help='ID usuario')

            args = parser.parse_args()

            print(args.items())
            _ID = args['ID']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('getPuerta', (_ID))
            data = cursor.fetchall()
            print(data)
            if len(data[0])== 2:
                #items_list = [];
                for item in data:
                    i = {
                        'Estado': item[1],
                    }
                    return item[1]
                    #items_list.append(i)
                #return jsonify(items_list)

            else:
                return 0
                #return jsonify(False)
                #return data[0][0];
        except Exception as e:
            return {'error': str(e)}

api.add_resource(GetPuerta, '/GetPuerta', methods=['POST'])

class UpdatePuerta(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('ID', type=str, help='ID usuario')
            parser.add_argument('Estado', type=str, help='Estado')
            args = parser.parse_args()

            print(args.items())
            _ID = args['ID']
            _Estado = args['Estado']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('updatePuerta', (_ID,_Estado))
            data = cursor.fetchall()

            print(data)
            if len(data) is 0:
                conn.commit()
                return jsonify(True)

            else:
                return data[0][0];


        except Exception as e:
            return {'error': str(e)}

api.add_resource(UpdatePuerta, '/UpdatePuerta', methods=['POST'])

@app.route('/')
def index():
	return "IDG 2017"



if __name__ == '__main__':
    app.run(
        #host=app.config.get("HOST", "172.24.181.48"),
        #port=app.config.get("PORT", 8080),
        #debug=True
    )

