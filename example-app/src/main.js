import './style.css';
import { Verisoul } from '@capgo/capacitor-verisoul';

const output = document.getElementById('plugin-output');
const projectIdInput = document.getElementById('project-id');
const environmentInput = document.getElementById('environment');
const configureButton = document.getElementById('configure');
const sessionButton = document.getElementById('get-session');
const reinitializeButton = document.getElementById('reinitialize');

const setOutput = (value) => {
  output.textContent = typeof value === 'string' ? value : JSON.stringify(value, null, 2);
};

configureButton.addEventListener('click', async () => {
  try {
    await Verisoul.configure({
      environment: environmentInput.value,
      projectId: projectIdInput.value,
    });
    setOutput('Configured');
  } catch (error) {
    setOutput(`Error: ${error?.message ?? error}`);
  }
});

sessionButton.addEventListener('click', async () => {
  try {
    const result = await Verisoul.getSessionId();
    setOutput(result);
  } catch (error) {
    setOutput(`Error: ${error?.message ?? error}`);
  }
});

reinitializeButton.addEventListener('click', async () => {
  try {
    await Verisoul.reinitialize();
    setOutput('Reinitialized');
  } catch (error) {
    setOutput(`Error: ${error?.message ?? error}`);
  }
});
