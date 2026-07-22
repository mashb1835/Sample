console.log("script.js 読み込み成功");

function clearForm() {
  document.getElementById('inputText').value = '';
  document.getElementById('reading').value = '';
  document.getElementById('meaning').value = '';
  document.querySelector('input[name="category"][value="kanji"]').checked = true;
}

function send() {
  showConfirmDialog();
}

function showConfirmDialog() {
  const category = document.querySelector('input[name="category"]:checked')?.value || '';
  document.getElementById('confirmCategory').textContent = category === 'kanji' ? '漢字' : '単語';
  document.getElementById('confirmText').textContent = document.getElementById('inputText').value;
  document.getElementById('confirmReading').textContent = document.getElementById('reading').value;
  document.getElementById('confirmMeaning').textContent = document.getElementById('meaning').value;
  document.getElementById('confirmDialog').classList.remove('hidden');
}

function closeConfirmDialog() {
  document.getElementById('confirmDialog').classList.add('hidden');
}

function executeAction() {
  const category = document.querySelector('input[name="category"]:checked')?.value || '';
  const payload = {
    category,
    inputText: document.getElementById('inputText').value,
    reading: document.getElementById('reading').value,
    meaning: document.getElementById('meaning').value
  };

  console.log('確認ダイアログ: 実行ボタンが押されました。送信データ:', payload);
  sendRequest(payload);
  closeConfirmDialog();
}

function sendRequest(payload) {
  console.log('送信リクエスト:', payload);

  fetch('api/convert', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(payload)
  })
  .then(res => res.json())
  .then(data => {
    console.log('戻り値:', data);
    // リダイレクト処理（クエリパラメータで登録した単語を渡す）
    window.location.href = `html/index2.html?word=${encodeURIComponent(data.word)}`;
  });
}