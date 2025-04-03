<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

	<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ラジオボタンのテスト</title>
    <style>
        /* 方法1: :checked スタイルをカスタマイズ */
        .method1 input[type="radio"] {
            appearance: none;
            width: 16px;
            height: 16px;
            border: 2px solid #ccc;
            border-radius: 50%;
            background-color: white;
            pointer-events: none;
        }

        .method1 input[type="radio"]:checked {
            background-color: blue;
            border-color: blue;
        }

        /* 方法2: label とカスタム要素を使用 */
        .method2 input[type="radio"] {
            display: none;
        }

        .method2 .custom-radio {
            display: inline-block;
            width: 16px;
            height: 16px;
            border-radius: 50%;
            border: 2px solid gray;
            background-color: white;
        }

        .method2 input[type="radio"]:checked + .custom-radio {
            background-color: blue;
            border-color: blue;
        }

        /* 方法3: opacity を利用 */
        .method3 input[type="radio"] {
            opacity: 0;
            position: absolute;
            pointer-events: none;
        }

        .method3 .custom-radio {
            width: 16px;
            height: 16px;
            display: inline-block;
            border-radius: 50%;
            border: 2px solid gray;
            background-color: white;
        }

        .method3 input[type="radio"]:checked + .custom-radio {
            background-color: red;
            border-color: red;
        }
    </style>
</head>
<body>
    <h1>ラジオボタンの色変更テスト</h1>

    <h2>方法1: :checked のスタイルをカスタマイズ</h2>
    <div class="method1">
        <input type="radio" name="test1" checked>
        <input type="radio" name="test1">
    </div>

    <h2>方法2: label を活用</h2>
    <div class="method2">
        <label>
            <input type="radio" name="test2" checked>
            <span class="custom-radio"></span>
        </label>
        <label>
            <input type="radio" name="test2">
            <span class="custom-radio"></span>
        </label>
    </div>

    <h2>方法3: opacity を利用</h2>
    <div class="method3">
        <label>
            <input type="radio" name="test3" checked>
            <span class="custom-radio"></span>
        </label>
        <label>
            <input type="radio" name="test3">
            <span class="custom-radio"></span>
        </label>
    </div>
</body>
</html>
