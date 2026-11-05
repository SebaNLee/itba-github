from flask import Flask, jsonify

app = Flask(__name__)


@app.route('/')
def hello_world():
    return "Hello, World!"

@app.route('/users')
def get_users():
     return jsonify([
        {"name": "Alice"},
        {"name": "Bob"},
        {"name": "Charlie"}
    ])



if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
