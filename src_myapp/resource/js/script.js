console.log("script.js 読み込み成功");

function send() {
  const text = document.getElementById('inputText').value;
  console.log('入力値:', text);

  fetch('api/convert', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ fruit: text })
  })
  .then(res => res.json())
  .then(data => {
    console.log('戻り値:', data);
    // リダイレクト処理（クエリパラメータで登録した単語を渡す）
    window.location.href = `html/index2.html?word=${encodeURIComponent(data.word)}`;
  });
}