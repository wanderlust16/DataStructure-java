package hw_4;
import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
		for(int i=0; i<value.length; i++) {
			for(int j=0; j<value.length-i-1; j++) {
				if(value[j] > value[j+1]) {
					int tmp = value[j+1];
					value[j+1] = value[j];
					value[j] = tmp;
				}
			}			
		}
		
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		for(int i=1; i<value.length; i++) {
			for(int j=0; j<i; j++) {
				if(value[j] > value[i]) {
					int tmp = value[i]; 
					for(int k=i-1; k>=j; k--) {
						value[k+1] = value[k];
					}
					value[j] = tmp;
					break;
				}
			}
		}
	
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// heap building 이후 최종적인 sorting만 해주는 곳 
		heapSort(value, value.length-1);
		return (value);
	}

	private static void heapSort(int[] arr, int n) {
		int parent;
		parent = n % 2 == 0 ? n/2-1 : n/2;
		for(int i=parent; i>=0; i--) {
			percolateDown(arr, i, n);
		}

		// after building heap 
		for(int i=n; i>=1; i--) {
			int tmp = arr[0];
			arr[0] = arr[i];
			arr[i] = tmp;
			percolateDown(arr, 0, i-1);
		}
	}
	
	private static void percolateDown(int[] arr, int i, int n) {
		int child = 2*i+1;
		int rightChild = 2*i+2;
		if(child <= n) {
			if((rightChild <= n) && (arr[child] < arr[rightChild])) {
				child = rightChild;
			}
			if(arr[i] < arr[child]) {
				int tmp = arr[i];
				arr[i] = arr[child];
				arr[child] = tmp;
				percolateDown(arr, child, n);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		mergeSort(value, 0, value.length-1);
		return (value);
	}
	
	private static void mergeSort(int[] arr, int low, int high) {
		if(high <= low) {
			return;
		} 
		int mid = (high + low) / 2;
		mergeSort(arr, low, mid);
		mergeSort(arr, mid+1, high);
		merge(arr, low, mid, high);
	}
		
	private static void merge(int[] arr, int low, int mid, int high) {
		int[] tmpArr = new int[high - low + 1];
		int i=low, j=mid+1, idx=0;
		
		while(i <= mid && j <= high) {
			if(arr[i] < arr[j]) {
				tmpArr[idx++] = arr[i++];
			} else {
				tmpArr[idx++] = arr[j++];
			}
		}
		
		while(i <= mid) {
			tmpArr[idx++] = arr[i++];
		}
		
		while(j <= high) {
			tmpArr[idx++] = arr[j++];
		}
		
		System.arraycopy(tmpArr, 0, arr, low, tmpArr.length);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
        quickSort(value, 0, value.length - 1);
		return (value);
	}
	
	private static void quickSort(int[] arr, int low, int high) {
		if(high <= low) {
			return;
		}
		int mid = partition(arr, low, high);
		quickSort(arr, low, mid-1);
		quickSort(arr, mid, high);
	}
	
	private static int partition(int[] arr, int low, int high) {
		int mid = (low + high) / 2;
		while(low <= high) {
			while(arr[low] < arr[mid]) low++;
			while(arr[high] > arr[mid]) high--;
			if(low <= high) {
				int tmp = arr[low];
				arr[low] = arr[high];
				arr[high] = tmp;
				
				low++;
				high--;
			}
		}	
		return low;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		ArrayList<Integer> negArr = new ArrayList<Integer>();
		ArrayList<Integer> posArr = new ArrayList<Integer>();
		
		// negative nums 
		for(int i=0; i<value.length; i++) {
			if(String.valueOf(Integer.toString(value[i]).charAt(0)) == "-") {
				negArr.add(value[i]);
			} else {
				posArr.add(value[i]);
			}
		}
		
		int[] negNums = new int[negArr.size()];
		int negSize = 0;
		for(int tmp : negArr) {
			negNums[negSize++] = tmp;
		}
		
		int[] posNums = new int[posArr.size()];
		int posSize = 0;
		for(int tmp : posArr) {
			posNums[posSize++] = tmp;
		}
		
		
		// get the biggest number 
		int max = value[0];
		for(int i=0; i<value.length; i++) {
			if(max < value[i]) {
				max = value[i];
			}
		}
		
		int maxRadix = (int)(Math.log10(max))+1; // 가장 큰 자릿수 
		
//		int[][] tmpArr = new int[10][value.length]; // 자리수별 stack 
//		ArrayList<Integer> output = new ArrayList<Integer>(); // 임시 arraylist  
		
		ArrayList<Integer>[] tmpArr = new ArrayList[10];
		
		int count = 0;
		while(count < maxRadix) {
			System.out.println("max" + maxRadix);
			System.out.println("filling"); 

			for(int i=0; i<value.length; i++) {
				int currNum = value[i] / (int)Math.pow(10, count);
				int r = currNum % 10;	
//				tmpArr[r][i] = value[i];
				// 중간에 빈 공간이 생기긴 하지만 i가 계속 올라가며, value.length 이상으로는 올라가지 않음
				// 중간에 생기는 빈 공간 메꿔주기?
				tmpArr[r].add(value[i]);
			}
			
//			for(int i=0; i<10; i++) {
//				for(int j=0; j<value.length; j++) {
//					System.out.print(tmpArr[i][j] + " ");
//				}
//				System.out.println();
//			}

			// value를 tmpArr값으로 순차적으로 채워준 후 tmpArr 비우기 
//			int k = 0;
//			for(int i=0; i<10; i++) {
//				for(int j=0; j<value.length; j++) {
////					output.add(tmpArr[i][j]);
//					if(tmpArr[i][j] != 0) value[k] = tmpArr[i][j];
//					if(k < value.length-1) k++;
//				}
//			}
			
			int k = 0;
			for(int i=0; i<10; i++) {
				for(int j=0; j<tmpArr[i].size(); j++) {
					value[k] = tmpArr[i].get(j);
					if(k < value.length-1) k++;
				}
			}
			
			// make array empty
			Arrays.fill(tmpArr, null);
			count++;
		}
		return (value);
	}
	
//	private static int[] radixSort(int[] arr) {
//		
//	}
}
