let questionCount = 1;

// Add new question
function addQuestion() {
    questionCount++;
    const container = document.getElementById("questionsContainer");
    const block = document.createElement("div");
    block.className = "question-block mb-4 p-3 shadow-sm";
    block.innerHTML = `
        <label class="form-label">Question ${questionCount}</label>
        <input type="text" class="form-control mb-2 shadow-sm" placeholder="Enter your question" required>

        <input type="text" class="form-control mb-2 shadow-sm" placeholder="Option A" required>
        <input type="text" class="form-control mb-2 shadow-sm" placeholder="Option B" required>
        <input type="text" class="form-control mb-2 shadow-sm" placeholder="Option C" required>
        <input type="text" class="form-control mb-2 shadow-sm" placeholder="Option D" required>

        <select class="form-select shadow-sm" required>
          <option value="">Select correct answer</option>
          <option value="A">Option A</option>
          <option value="B">Option B</option>
          <option value="C">Option C</option>
          <option value="D">Option D</option>
        </select>
    `;
    container.appendChild(block);
}

// Save Quiz + Preview
document.getElementById("quizForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const title = document.getElementById("quizTitle").value;
    const timer = document.getElementById("quizTimer").value;
    const questions = document.querySelectorAll(".question-block");

    let previewHTML = `<h5>${title}</h5><p>⏱ Timer: <strong>${timer} minutes</strong></p><ol>`;
    questions.forEach((q) => {
        const qText = q.querySelector("input").value;
        const opts = q.querySelectorAll("input[type=text]");
        const correct = q.querySelector("select").value;

        previewHTML += `<li><strong>${qText}</strong><ul>`;
        opts.forEach((opt, i) => {
            const label = ["A", "B", "C", "D"][i];
            previewHTML += `<li>${label}: ${opt.value} ${label === correct ? "✅" : ""}</li>`;
        });
        previewHTML += "</ul></li>";
    });
    previewHTML += "</ol>";

    document.getElementById("previewContent").innerHTML = previewHTML;
    document.getElementById("quizPreview").style.display = "block";
    window.scrollTo({ top: document.getElementById("quizPreview").offsetTop, behavior: "smooth" });
});
