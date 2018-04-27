public class quicksort {

    static public void quick_sort(int []nums, int low,int high){
        if(low < high){
            int mid = find_mid(nums,low,high);
            quick_sort(nums,low,mid-1);
            quick_sort(nums,mid+1,high);
        }
    }

    static public int find_mid(int[] nums, int low,int high){
        int temp = nums[low];
        while (low < high){
            while (low < high && nums[high] >= temp)
                high--;
            if (low < high) nums[low++]=nums[high];
            while (low < high && nums[low] <= temp)
                low++;
            if (low < high) nums[high--]=nums[low];
        }
        nums[low]=temp;
        return low;
    }

    static public void main(String []args){
        int [] nums = {6,5,92,44,11,63,1,9,6,55};
        quick_sort(nums,0,nums.length-1);
        for (int i = 0; i<nums.length; i++)
            System.out.print(nums[i] + " ");
    }

}
